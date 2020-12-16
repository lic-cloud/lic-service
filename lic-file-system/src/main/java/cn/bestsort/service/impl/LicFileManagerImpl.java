package cn.bestsort.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.dto.FileDTO;
import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.Status;
import cn.bestsort.model.param.UploadSuccessCallbackParam;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.model.vo.UploadTokenVO;
import cn.bestsort.service.FileInfoService;
import cn.bestsort.service.FileManager;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.FileShareService;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.UserService;
import cn.bestsort.util.UrlUtil;
import cn.bestsort.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 17:36
 */
@Slf4j
@Service
public class LicFileManagerImpl implements LicFileManager {

    final FileManagerHandler    manager;
    final FileInfoService    fileInfoImp;
    final FileMappingService mappingService;
    final FileShareService   fileShareImpl;
    final UserService userService;

    @Override
    public boolean existMd5(String md5, FileNamespace fileNamespace) {
        return fileInfoImp.getByMd5(md5, fileNamespace) != null;
    }


    @Override
    public String createDownloadLink(Long mappingId, Long expire) {
        FileMapping mapping = mappingService.getById(mappingId);
        if (mapping.getIsDir()) {
            throw ExceptionConstant.MUST_BE_NOT_DIR;
        }

        FileInfo info = fileInfoImp.getById(mapping.getInfoId());
        return UrlUtil.appendParam(
            manager.handle(info).downloadLink(info.getPath() + info.getFileName(), expire),
                                   "fileName",
            URLEncoder.encode(mapping.getFileName(), StandardCharsets.UTF_8));
    }

    @Override
    public UploadTokenVO createUploadToken(FileNamespace namespace, Map<String, String> config) {
        return manager.handle(namespace).generatorUploadVO(config);
    }

    @Override
    public void uploadSuccess(UploadSuccessCallbackParam param) {
        FileInfo info = fileInfoImp.getByMd5(param.getMd5(), param.getNamespace());
        LoginUserVO loginUser = UserUtil.mustGetLoginUser();
        if (info != null) {
            info.setReference(info.getReference() + 1);
        } else {
            info = new FileInfo(mappingService.fullPath(param.getPid()), loginUser.getUsername(),
                                param.getEntityName(), 1, param.getMd5(), param.getSize(),
                                param.getNamespace(), null);
            info = fileInfoImp.save(info);
        }
        FileMapping fileMapping = new FileMapping(param.getName(), info.getId(), loginUser.getId(), param.getSize(),
                                                  param.getPid(), false, false, Status.VALID);
        if (param.getFinished()) {
            User sysUser = userService.getById(loginUser.getId());
            sysUser.setTotalCapacity(sysUser.getUsedCapacity() + param.getSize());
            userService.save(sysUser);
        }
        mappingService.save(fileMapping);
    }

    @Override
    public void createMapping(FileMapping mapping) {
        UserUtil.checkIsOwner(mapping.getOwnerId());
        List<FileMapping> mappings = mappingService.listFiles(mapping.getPid());
        mappings.forEach(i -> {
            if (i.getFileName().equals(mapping.getFileName())) {
                throw ExceptionConstant.TARGET_EXIST;
            }
        });
        mappingService.create(mapping);
    }

    @Override
    public void deleteFile(Long fileId, boolean remove) {
        Optional<FileMapping>             fileMapping = mappingService.fetchById(fileId);
        Map<FileNamespace, List<FileDTO>> map         = new TreeMap<>();
        fileMapping.ifPresent(mapping -> deleteSoftLink(mapping, remove, map));
        // 部分OSS支持删除文件列表, 防止多次创建连接造成的时间消耗
        for (Map.Entry<FileNamespace, List<FileDTO>> pir : map.entrySet()) {
            handle(pir.getKey()).del(
                pir.getValue().stream().map(FileDTO::getFileInfo).collect(Collectors.toList())
            );
        }
    }

    /**
     * 1. 若为文件夹， 递归删除
     * 2. 若无映射指向文件实体，删除文件实体
     * 3. 文件实体 Reference-1
     */
    private void deleteSoftLink(FileMapping fileMapping, boolean remove,
        Map<FileNamespace, List<FileDTO>> needRemove) {
        if (fileMapping == null) {
            return;
        }
        if (fileMapping.getIsDir()) {
            List<FileMapping> fileMappings = null;
            //TODO listFiles(fileMapping.getId(), user.getId(), Status.VALID);
            for (FileMapping mapping : fileMappings) {
                deleteSoftLink(mapping, remove, needRemove);
            }
        }
        if (remove) {
            Optional<FileInfo> fileInfoOpt = fileInfoImp.fetchById(fileMapping.getInfoId());
            if (fileInfoOpt.isPresent()) {
                FileInfo fileInfo = fileInfoOpt.get();
                // 删除文件映射后再无映射指向该文件实体, 物理删除
                if (fileInfo.getReference() <= 1) {
                    fileInfoImp.removeById(fileInfo.getId());
                    FileDTO fileDTO = new FileDTO(UserUtil.getLoginUserId(), fileInfo.getNamespace(), fileInfo, null);
                    if (!needRemove.containsKey(fileDTO.getNamespace())) {
                        needRemove.put(fileDTO.getNamespace(), new LinkedList<>());
                    }
                    needRemove.get(fileDTO.getNamespace()).add(fileDTO);
                } else {
                    // 否则文件引用-1
                    fileInfo.setReference(fileInfo.getReference() - 1);
                    fileInfoImp.save(fileInfo);
                }
            }
        }
        fileMapping.setStatus(Status.INVALID);
        mappingService.save(fileMapping);
    }

    private FileManager handle(FileNamespace nameSpace) {
        return manager.handle(nameSpace);
    }

    public LicFileManagerImpl(FileManagerHandler manager, FileInfoService fileInfoImp,
                              FileMappingService mappingService, FileShareService fileShareImpl,
                              UserService userService) {
        this.userService = userService;
        this.manager        = manager;
        this.fileInfoImp    = fileInfoImp;
        this.mappingService = mappingService;
        this.fileShareImpl  = fileShareImpl;
    }
}

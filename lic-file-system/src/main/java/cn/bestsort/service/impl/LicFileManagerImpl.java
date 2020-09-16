package cn.bestsort.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cn.bestsort.model.dto.FileDTO;
import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.FileShare;
import cn.bestsort.model.entity.user.User;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.Status;
import cn.bestsort.model.param.ShareParam;
import cn.bestsort.service.FileInfoService;
import cn.bestsort.service.FileManager;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.FileShareService;
import cn.bestsort.service.LicFileManager;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 17:36
 */
@Service
public class LicFileManagerImpl implements LicFileManager {

    final FileManagerHandler    manager;
    final FileInfoService     fileInfoImp;
    final FileMappingService fileMappingImpl;
    final FileShareService   fileShareImpl;


    @Override
    public List<FileMapping> listFiles(Long dirId, User user) {
        return fileMappingImpl.listUserFile(dirId, user.getId());
    }


    @Override
    public String createDownloadLink(Long fileId, User user, Long expire) {
        FileInfo info = fileInfoImp.getById(fileId);
        return manager.handle(info).downloadLink(new FileDTO(info, user), expire);
    }


    @Override
    public String createShareLink(ShareParam param, User user) {
        String url;
        do {
            // 防止生成的url产生碰撞
            url = RandomStringUtils.randomAlphanumeric(16);
        } while (fileShareImpl.existsByUrl(url));

        fileShareImpl.save(new FileShare(param.getFileId(), user.getUsername(),
            user.getId(), param.getPassword(), url, param.getExpire()));
        return url;
    }

    @Override
    public void deleteFile(Long fileId, User user, boolean remove) {
        Optional<FileMapping>             fileMapping = fileMappingImpl.fetchById(fileId);
        Map<FileNamespace, List<FileDTO>> map         = new TreeMap<>();
        fileMapping.ifPresent(mapping -> deleteSoftLink(mapping, user, remove, map));
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
    private void deleteSoftLink(FileMapping fileMapping, User user, boolean remove,
        Map<FileNamespace, List<FileDTO>> needRemove) {
        if (fileMapping == null) {
            return;
        }
        if (fileMapping.getIsDir()) {
            List<FileMapping> fileMappings = listFiles(fileMapping.getId(), user);
            for (FileMapping mapping : fileMappings) {
                deleteSoftLink(mapping, user, remove, needRemove);
            }
        }
        if (remove) {
            Optional<FileInfo> fileInfoOpt = fileInfoImp.fetchById(fileMapping.getInfoId());
            if (fileInfoOpt.isPresent()) {
                FileInfo fileInfo = fileInfoOpt.get();
                // 删除文件映射后再无映射指向该文件实体, 物理删除
                if (fileInfo.getReference() <= 1) {
                    fileInfoImp.removeById(fileInfo.getId());
                    FileDTO fileDTO = new FileDTO(user.getId(), fileInfo.getNamespace(), fileInfo, null);
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
        fileMappingImpl.save(fileMapping);
    }

    private FileManager handle(FileNamespace nameSpace) {
        return manager.handle(nameSpace);
    }


    public LicFileManagerImpl(FileManagerHandler manager, FileInfoService fileInfoImp,
                              FileMappingService fileMappingImpl, FileShareService fileShareImpl) {
        this.manager = manager;
        this.fileInfoImp = fileInfoImp;
        this.fileMappingImpl = fileMappingImpl;
        this.fileShareImpl = fileShareImpl;
    }
}

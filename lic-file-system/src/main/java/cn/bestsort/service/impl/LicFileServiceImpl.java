package cn.bestsort.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cn.bestsort.dto.FileDTO;
import cn.bestsort.entity.FileInfo;
import cn.bestsort.entity.FileMapping;
import cn.bestsort.entity.user.User;
import cn.bestsort.enums.FileNamespace;
import cn.bestsort.enums.Status;
import cn.bestsort.repository.FileInfoRepository;
import cn.bestsort.repository.FileMappingRepository;
import cn.bestsort.service.FileManager;
import cn.bestsort.service.FileManagerHandler;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 17:36
 */
@Service
public class LicFileServiceImpl implements cn.bestsort.service.LicFileService {

    final FileManagerHandler manager;
    final FileInfoRepository fileInfoRepo;
    final FileMappingRepository fileMappingRepo;

    @Override
    public List<FileMapping> listFiles(Long dirId, User user) {
        return fileMappingRepo.findAllByPidAndOwnerIdAndStatus(dirId, user.getId(), Status.VALID);
    }
    

    @Override
    public void deleteFile(Long fileId, User user, boolean remove) {
        Optional<FileMapping>             fileMapping = fileMappingRepo.findById(fileId);
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
     * 3. 文件实体 Reference--
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
            Optional<FileInfo> fileInfoOpt = fileInfoRepo.findById(fileMapping.getInfoId());
            if (fileInfoOpt.isPresent()) {
                FileInfo fileInfo = fileInfoOpt.get();
                // 删除文件映射后再无映射指向该文件实体, 物理删除
                if (fileInfo.getReference() <= 1) {
                    fileInfoRepo.deleteById(fileInfo.getId());
                    FileDTO fileDTO = new FileDTO(user.getId(), fileInfo.getNamespace(), fileInfo, null);
                    if (!needRemove.containsKey(fileDTO.getNamespace())) {
                        needRemove.put(fileDTO.getNamespace(), new LinkedList<>());
                    }
                    needRemove.get(fileDTO.getNamespace()).add(fileDTO);
                } else {
                    // 否则文件引用-1
                    fileInfo.setReference(fileInfo.getReference() - 1);
                    fileInfoRepo.saveAndFlush(fileInfo);
                }
            }
        }
        fileMapping.setStatus(Status.INVALID);
        fileMappingRepo.saveAndFlush(fileMapping);
    }

    private FileManager handle(FileNamespace nameSpace) {
        return manager.handle(nameSpace);
    }

    public LicFileServiceImpl(FileManagerHandler manager,
        FileInfoRepository fileInfoRepo, FileMappingRepository fileMappingRepo) {
        this.manager = manager;
        this.fileInfoRepo = fileInfoRepo;
        this.fileMappingRepo = fileMappingRepo;
    }
}

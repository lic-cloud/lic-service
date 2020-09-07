package cn.bestsort.service.impl;

import java.util.List;
import java.util.Optional;

import cn.bestsort.entity.FileMapping;
import cn.bestsort.entity.user.User;
import cn.bestsort.enums.FileNamespace;
import cn.bestsort.enums.Status;
import cn.bestsort.repository.FileMappingRepository;
import cn.bestsort.service.FileManager;
import cn.bestsort.service.FileManagerHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 17:36
 */
@Service
public class LicFileServiceImpl {

    final FileManagerHandler manager;

    final FileMappingRepository fileMappingRepo;

    public List<FileMapping> listFiles(Long dirId, User user) {
        return  fileMappingRepo.findAllByPidAndOwnerIdAndStatus(dirId, user.getId(), Status.VALID);
    }


    @Transactional(rollbackFor = Exception.class)
    public void del(Long fileId, User user, boolean remove) {
        Optional<FileMapping> fileMapping = fileMappingRepo.findById(fileId);
        fileMapping.ifPresent(mapping -> del(mapping, user, remove));
    }


    /**
     * 逻辑删除文件
     * @param fileMapping 文件实体
     * @param user        对应用户
     * @param remove      是否永久删除
     */
    private void del(FileMapping fileMapping, User user, boolean remove) {
        if (fileMapping == null) {
            return;
        }
        if (fileMapping.getIsDir()) {
            List<FileMapping> fileMappings = listFiles(fileMapping.getId(), user);
            for (FileMapping mapping : fileMappings) {
                del(mapping, user, remove);
            }
        } else {
            if (remove) {
                //TODO 引用-1, 为0删除文件实体
            }
            fileMapping.setStatus(Status.INVALID);
            fileMappingRepo.saveAndFlush(fileMapping);
        }
    }

    private FileManager handle(FileNamespace nameSpace) {
        return manager.handle(nameSpace);
    }

    public LicFileServiceImpl(FileManagerHandler manager,
        FileMappingRepository fileMappingRepo) {
        this.manager = manager;
        this.fileMappingRepo = fileMappingRepo;
    }
}

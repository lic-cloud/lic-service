package cn.bestsort.service.impl;

import java.io.File;
import java.util.List;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.Status;
import cn.bestsort.repository.FileMappingRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.FileMappingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:31
 */
@Service
public class FileMappingServiceImpl extends AbstractBaseService<FileMapping, Long> implements FileMappingService {

    final FileMappingRepository repo;

    @Override
    public Page<FileMapping> listUserFiles(Pageable page, Long pid, Long userId,
                                                 Status status, Boolean onlyDir) {
        return repo.findAllByPidAndOwnerIdAndStatusAndIsDir(page, pid, userId, status, onlyDir);
    }

    @Override
    public List<FileMapping> listUserFilesWithoutPage(Long dirId, Long userId, Status status, Boolean onlyDir) {
        return repo.findAllByPidAndOwnerIdAndStatusAndIsDir(dirId, userId, status, onlyDir);
    }


    @Override
    public String fullPath(Long dirId) {

        if (dirId == 0) {
            return File.separator;
        }
        FileMapping fileMapping;
        StringBuilder  res = new StringBuilder();
        // 不断向上回溯父路径
        do {
            fileMapping = getById(dirId);
            res.append(fileMapping.getFileName()).append(File.separator);
        } while (fileMapping.getPid() != 0);
        return res.reverse().toString();
    }


    protected FileMappingServiceImpl(
        FileMappingRepository repository) {
        super(repository);
        repo = repository;
    }

}

package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.Status;
import cn.bestsort.repository.FileMappingRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.FileMappingService;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:31
 */
public class FileMappingServiceImpl extends AbstractBaseService<FileMapping, Long> implements FileMappingService {

    final FileMappingRepository repo;


    @Override
    public List<FileMapping> listUserFile(Long dirId, Long userId) {
        return repo.findAllByPidAndOwnerIdAndStatus(dirId, userId, Status.VALID);
    }


    protected FileMappingServiceImpl(
        FileMappingRepository repository) {
        super(repository);
        repo = repository;
    }
}

package cn.bestsort.service.impl;

import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.repository.FileInfoRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.FileInfoService;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:21
 */
@Service
public class FileInfoServiceImpl extends AbstractBaseService<FileInfo, Long> implements FileInfoService {



    @Override
    public FileInfo getByMd5(String md5, FileNamespace namespace) {
        return repo.getFirstByMd5AndNamespace(md5, namespace);
    }

    FileInfoRepository repo;
    protected FileInfoServiceImpl(
        FileInfoRepository repository) {
        super(repository);
        this.repo = repository;
    }
}

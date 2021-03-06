package cn.bestsort.service.impl;

import java.util.Optional;

import cn.bestsort.model.entity.FileShare;
import cn.bestsort.repository.FileShareRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.FileShareService;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:32
 */
@Service
public class FileShareImpl extends AbstractBaseService<FileShare, Long>
    implements FileShareService {

    final FileShareRepository repo;

    @Override
    public boolean existsByUrl(String url) {
        return repo.existsByUrl(url);
    }

    @Override
    public Optional<FileShare> getByUrl(String url) {
        return repo.getFileShareByUrl(url);
    }

    @Override
    public Optional<FileShare> getByMappingId(Long mappingId) {
        return repo.getByMappingId(mappingId);
    }

    protected FileShareImpl(
        FileShareRepository repository) {
        super(repository);
        repo = repository;
    }

}

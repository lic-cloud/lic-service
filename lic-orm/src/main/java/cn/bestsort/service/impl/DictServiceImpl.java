package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.repository.DictRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.DictService;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 13:56
 */
@Service
public class DictServiceImpl extends AbstractBaseService<Dict, Long> implements DictService {
    DictRepository repo;

    @Override
    public Dict findByTypeAndKey(String type, String key) {
        return repo.findByTypeAndK(type, key);
    }

    @Override
    public List<Dict> findAllByKey(String type) {
        return repo.findAllByType(type);
    }


    protected DictServiceImpl(DictRepository repository) {
        super(repository);
        repo = repository;
    }
}

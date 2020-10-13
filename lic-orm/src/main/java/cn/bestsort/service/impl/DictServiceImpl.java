package cn.bestsort.service.impl;

import java.util.List;
import java.util.Map;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.repository.DictRepository;
import cn.bestsort.repository.impl.RepositoryEntity;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public int count(Map<String, Object> params) {
        String type = (String) params.get("type");
        return repo.count(type);
    }

    @Autowired
    private RepositoryEntity rre;

    @Override
    public List<Dict> list(Map<String, Object> params, int offset, int limit) {
        String type = (String) params.get("type");
        String orderBy = (String) params.get("orderBy");
        return rre.listDict(type, orderBy, offset, limit);
    }


    protected DictServiceImpl(DictRepository repository) {
        super(repository);
        repo = repository;
    }
}

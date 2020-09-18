package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.model.entity.RoleUser;
import cn.bestsort.repository.RoleUserRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.RoleUserService;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 15:07
 */
@Service
public class RoleUserImpl extends AbstractBaseService<RoleUser, Long> implements RoleUserService {

    RoleUserRepository repo;
    @Override
    public List<RoleUser> listByRoleId(Long id) {
        return repo.findAllByRoleId(id);
    }

    @Override
    public List<RoleUser> listByUserId(Long id) {
        return repo.findAllByUserId(id);
    }

    protected RoleUserImpl(
        RoleUserRepository repository) {
        super(repository);
        repo = repository;
    }
}

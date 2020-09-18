package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.model.entity.NoticeRead;
import cn.bestsort.repository.NoticeReadRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.NoticeReadService;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-18 07:57
 */
@Service
public class NoticeReadImpl extends AbstractBaseService<NoticeRead, Long>
    implements NoticeReadService {

    NoticeReadRepository repo;

    @Override
    public List<NoticeRead> findAllByNoticeId(Long id) {
        return repo.findAllByNoticeId(id);
    }

    @Override
    public List<NoticeRead> findAllByUserId(Long id) {
        return repo.findAllByUserId(id);
    }

    protected NoticeReadImpl(
        NoticeReadRepository repository) {
        super(repository);
        repo = repository;
    }
}

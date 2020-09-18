package cn.bestsort.service.impl;

import cn.bestsort.model.entity.Notice;
import cn.bestsort.repository.NoticeRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 15:18
 */
@Service
public class NoticeServiceImpl extends AbstractBaseService<Notice, Long> implements NoticeService {

    NoticeRepository repo;

    protected NoticeServiceImpl(
        NoticeRepository repository) {
        super(repository);
        repo = repository;
    }
}

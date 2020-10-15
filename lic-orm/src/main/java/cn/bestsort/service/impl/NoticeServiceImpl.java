package cn.bestsort.service.impl;

import java.util.List;
import java.util.Map;

import cn.bestsort.model.entity.Notice;
import cn.bestsort.repository.NoticeRepository;
import cn.bestsort.repository.impl.RepositoryEntity;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 15:18
 */
@Service
public class NoticeServiceImpl extends AbstractBaseService<Notice, Long>
    implements NoticeService {

    NoticeRepository repo;

    protected NoticeServiceImpl(
        NoticeRepository repository) {
        super(repository);
        repo = repository;
    }

    @Override
    public int count(Map<String, Object> params) {
        String title = (String) params.get("title");
        String status = (String) params.get("status");
        /*
        Time beginTime = (Time) params.get("beginTime");
        Time endTime = (Time) params.get("endTime");
        */
        if ("".equals(status)) {
            return repo.count(title, null);
        }
        return repo.count(title, Integer.valueOf(status));
    }

    @Autowired
    private RepositoryEntity rre;

    @Override
    public List<Notice> list(Map<String, Object> params, int offset, int limit) {
        String title = (String) params.get("title");
        String status = (String) params.get("status");
        /*Time beginTime = (Time) params.get("beginTime");
        Time endTime = (Time) params.get("endTime");*/
        String orderBy = (String) params.get("orderBy");
        if ("".equals(status)) {
            return rre.listNotice(title, null, orderBy, offset, limit);
        }
        return rre.listNotice(title, Integer.valueOf(status), orderBy, offset, limit);
    }
}

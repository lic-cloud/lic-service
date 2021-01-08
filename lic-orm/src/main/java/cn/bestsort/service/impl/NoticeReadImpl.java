package cn.bestsort.service.impl;

import cn.bestsort.model.entity.NoticeRead;
import cn.bestsort.model.vo.NoticeReadVO;
import cn.bestsort.repository.NoticeReadRepository;
import cn.bestsort.repository.impl.RepositoryEntity;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.NoticeReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-18 07:57
 */
@Service
public class NoticeReadImpl extends AbstractBaseService<NoticeRead, Long> implements NoticeReadService {
    @Autowired
    private NoticeReadRepository repo;
    @Autowired
    private RepositoryEntity rre;

    @Override
    public void deleteAllByNoticeId(Long id) {
        repo.deleteAllByNoticeId(id);
    }

    @Override
    public List<NoticeRead> findAllByUserIdAndNoticeId(Long userId, Long noticeId) {
        return repo.findAllByUserIdAndNoticeId(userId,noticeId);
    }

    @Override
    public int countUnread(Long userId) {
        return repo.countUnread(userId);
    }


    protected NoticeReadImpl(
        NoticeReadRepository repository) {
        super(repository);
        repo = repository;
    }

    @Override
    public int count(Map<String, Object> params) {
        String title = (String) params.get("title");
        String isRead = (String) params.get("isRead");
        Long userId = (Long) params.get("userId");
        if ("".equals(isRead)) {
            return repo.count(userId.intValue(), title, null);
        }
        return repo.count(userId.intValue(), title, Integer.valueOf(isRead));
    }

    @Override
    public List<NoticeReadVO> list(Map<String, Object> params, int offset, int limit) {
        String title = (String) params.get("title");
        String isRead = (String) params.get("isRead");
        Long userId = (Long) params.get("userId");
        String orderBy = (String) params.get("orderBy");
        if ("".equals(isRead)) {
            return rre.listNoticeRead(userId.intValue(), title, null, orderBy, offset, limit);
        }
        return rre.listNoticeRead(userId.intValue(), title, Integer.valueOf(isRead), orderBy, offset, limit);
    }
}

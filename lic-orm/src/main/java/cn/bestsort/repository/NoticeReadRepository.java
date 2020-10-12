package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.NoticeRead;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 19:44
 */
public interface NoticeReadRepository extends BaseRepository<NoticeRead, Long> {
    List<NoticeRead> findAllByNoticeId(Long id);

    List<NoticeRead> findAllByUserIdAndNoticeId(Long userId,Long noticeId);

    void deleteAllByNoticeId(Long id);

    @Query(value = "select count(1) from t_notice t left join t_notice_read r on r.notice_id = t.id and r.user_id = ?1 where t.status = 1 and r.user_id is null ", nativeQuery = true)
    int countUnread(Long userId);

    @Query(value = "select count(1) from t_notice t left join t_notice_read r on r.notice_id = t.id and r.user_id = ?1 where t.status = 1 and (t.title like ?2 or ?2='') and if(?3=0,r.create_at is null,1=1) and if(?3=1,r.create_at is not null,1=1)", nativeQuery = true)
    int count(Integer userId, String title,Integer isRead);
}

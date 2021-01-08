package cn.bestsort.repository;

import cn.bestsort.model.entity.NoticeRead;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 19:44
 */
public interface NoticeReadRepository extends BaseRepository<NoticeRead, Long> {


    /**
     * 依据通知id,用户id获得NoticeRead实例集合
     *
     * @param userId
     * @param noticeId
     * @return
     */
    List<NoticeRead> findAllByUserIdAndNoticeId(Long userId, Long noticeId);

    /**
     * 依据通知id删除通知
     *
     * @param id
     */
    void deleteAllByNoticeId(Long id);

    /**
     * 统计符合条件的列表数据未读个数
     *
     * @param userId
     * @return
     */
    @Query(value = "select count(1) from notice t left join notice_read r on r.notice_id = t.id and r.user_id = ?1 where t.status = 1 and r.user_id is null ", nativeQuery = true)
    int countUnread(Long userId);

    /**
     * 统计符合条件的列表数据全部个数
     *
     * @param userId
     * @param title
     * @param isRead
     * @return
     */
    @Query(value = "select count(1) from notice t left join notice_read r on r.notice_id = t.id and r.user_id = ?1 where t.status = 1 and (t.title like ?2 or ?2='') and if(?3=0,r.create_at is null,1=1) and if(?3=1,r.create_at is not null,1=1)", nativeQuery = true)
    int count(Integer userId, String title, Integer isRead);
}

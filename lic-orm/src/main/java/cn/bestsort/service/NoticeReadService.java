package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.NoticeRead;
import cn.bestsort.model.vo.NoticeReadVO;
import cn.bestsort.util.page.Listable;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-18 07:56
 */
public interface NoticeReadService extends BaseService<NoticeRead, Long>, Listable<NoticeReadVO> {
    /**
     * 依据通知id删除通知
     *
     * @param id
     */
    void deleteAllByNoticeId(Long id);

    /**
     * 依据通知id,用户id获得NoticeRead实例集合
     *
     * @param userId
     * @param noticeId
     * @return
     */
    List<NoticeRead> findAllByUserIdAndNoticeId(Long userId, Long noticeId);

    /**
     * 统计符合条件的列表数据未读个数
     *
     * @param userId
     * @return
     */
    int countUnread(Long userId);
}

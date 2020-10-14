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
     * 依据通知id获取已读通知集合
     *
     * @param id
     * @return
     */
    List<NoticeRead> findAllByNoticeId(Long id);

}

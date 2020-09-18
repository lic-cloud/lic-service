package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.NoticeRead;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-18 07:56
 */
public interface NoticeReadService extends BaseService<NoticeRead, Long> {
    List<NoticeRead> findAllByNoticeId(Long id);

    List<NoticeRead> findAllByUserId(Long id);

}

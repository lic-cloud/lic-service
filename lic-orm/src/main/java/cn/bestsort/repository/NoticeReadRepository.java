package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.NoticeRead;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 19:44
 */
public interface NoticeReadRepository extends BaseRepository<NoticeRead, Long> {
    List<NoticeRead> findAllByNoticeId(Long id);

    List<NoticeRead> findAllByUserId(Long id);
}

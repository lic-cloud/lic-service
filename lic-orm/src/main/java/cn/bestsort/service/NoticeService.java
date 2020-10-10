package cn.bestsort.service;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.model.entity.Notice;

import java.util.List;
import java.util.Map;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 15:18
 */
public interface NoticeService extends BaseService<Notice, Long> {

    int countNotice(Map<String, Object> params);

    List<Notice> listNotice(Map<String, Object> params, int offset, int limit);
}

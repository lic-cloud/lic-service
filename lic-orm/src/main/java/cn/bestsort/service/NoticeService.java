package cn.bestsort.service;

import cn.bestsort.model.entity.Notice;
import cn.bestsort.util.page.Listable;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 15:18
 */
public interface NoticeService extends BaseService<Notice, Long>, Listable<Notice> {

    /**
     * 依据id查询获得通知实例
     *
     * @param id
     * @return
     */
    Notice findAllById(Long id);

    /**
     * 依据id删除通知
     * @param id
     */
    void deleteById(Long id);
}

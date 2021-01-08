package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.util.page.Listable;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 13:55
 */
public interface DictService extends BaseService<Dict, Long>, Listable<Dict> {
    /**
     * 依据type,key获取字典实例
     *
     * @param type
     * @param key
     * @return
     */
    Dict findByTypeAndKey(String type, String key);

    /**
     * 依据type获取字典实例集合
     *
     * @param type
     * @return
     */
    List<Dict> findAllByType(String type);

    /**
     * 依据id获取字典
     * @param id
     * @return
     */
    Dict findAllById(Long id);
}

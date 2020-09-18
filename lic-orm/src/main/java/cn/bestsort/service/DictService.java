package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.Dict;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 13:55
 */
public interface DictService extends BaseService<Dict, Long> {
    Dict findByTypeAndKey(String type, String key);
    List<Dict> findAllByKey(String type);

}

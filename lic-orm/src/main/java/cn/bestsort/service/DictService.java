package cn.bestsort.service;

import java.util.List;
import java.util.Map;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.model.entity.Role;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 13:55
 */
public interface DictService extends BaseService<Dict, Long> {
    Dict findByTypeAndKey(String type, String key);
    List<Dict> findAllByKey(String type);

    int countDict(Map<String, Object> params);

    List<Dict> listDict(Map<String, Object> params, int offset, int limit);
}

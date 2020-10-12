package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.util.page.Listable;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 13:55
 */
public interface DictService extends BaseService<Dict, Long>, Listable<Dict> {
    Dict findByTypeAndKey(String type, String key);

    List<Dict> findAllByKey(String type);

}

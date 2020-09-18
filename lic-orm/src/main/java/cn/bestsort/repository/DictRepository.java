package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.Dict;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 13:53
 */
public interface DictRepository extends BaseRepository<Dict, Long> {
    Dict findByTypeAndK(String type, String key);
    List<Dict> findAllByType(String type);
}

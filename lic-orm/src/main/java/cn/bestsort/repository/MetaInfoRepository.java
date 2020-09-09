package cn.bestsort.repository;

import java.util.Optional;

import cn.bestsort.entity.MetaInfo;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:15
 */
public interface MetaInfoRepository extends BaseRepository<MetaInfo, Long> {
    Optional<MetaInfo> findByMetaKey(String key);
}

package cn.bestsort.cache.store;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.bestsort.cache.CacheStoreType;
import cn.bestsort.cache.ext.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-16 08:17
 */
@Slf4j
@Service
public class RedisCacheStoreImpl extends AbstractCacheStore<String, String> implements CacheStore<String, String> {
    @Autowired
    RedisManager manager;

    @Override
    public Optional<String> getInternal(String key) {
        return Optional.ofNullable(manager.get(key));
    }

    @Override
    public void sortedListAdd(String listName, Map<String, Double> scoreValMap) {
        manager.sortedListAdd(listName, scoreValMap);
    }

    @Override
    public void sortedListAdd(String listName, String value, double score) {
        manager.sortedListAdd(listName, value, score);
    }


    @Override
    public void put(String key, String value, long timeout, TimeUnit timeUnit) {
        manager.put(key, value, timeout, timeUnit);
    }

    @Override
    public Set<Tuple> fetchRanksByPage(String listName, Pageable pageable, boolean less) {
        return manager.fetchRanksByPage(listName, pageable, less);
    }

    @Override
    public void inc(@NonNull String key, int stepLength) {
        log.debug("inc : [ {} ],step is {}", key, stepLength);
        manager.inc(key, stepLength);
    }

    @Override
    public Long totalElementOfList(@NonNull String key) {
        return manager.totalElementOfList(key);
    }

    @Override
    public CacheStoreType getCacheType() {
        return CacheStoreType.REDIS;
    }

    @Override
    public void delete(String key) {
        manager.delete(key);
    }

    @Override
    public void delete(Collection<String> collection) {
        for (String s : collection) {
            delete(s);
        }
    }

    @Override
    public Map<String, String> fetchAll(String prefix) {
        return manager.fetchAll(prefix);
    }

    @Override
    public Long ttl(String key) {
        return manager.ttl(key);
    }

    @Override
    public Long zRank(String key, String member, boolean less) {
        return manager.zRank(key, member, less);
    }

    @Override
    public void clearCachePool() {

    }

    @Override
    public void init() {

    }
}

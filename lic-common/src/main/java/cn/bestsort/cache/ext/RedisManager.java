package cn.bestsort.cache.ext;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Pageable;
import redis.clients.jedis.Tuple;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-16 08:18
 */
public interface RedisManager {
    String get(String key);

    Map<String, String> fetchAll(String prefix);

    Set<Tuple> fetchRanksByPage(String listName, Pageable pageable, boolean less);

    void delete(String key);

    void put(String key, String value, long timeOut, TimeUnit timeUnit);

    void inc(String key, int stepLength);

    void sortedListAdd(String key, Map<String, Double> doubleStringMap);

    void sortedListAdd(String key, String value, double score);

    Long totalElementOfList(String key);

    Long ttl(String key);

    Long zRank(String key, String member, boolean less);
}

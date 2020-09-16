package cn.bestsort.cache.ext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.SetParams;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-16 08:20
 */

@Service
public class RedisManagerImpl implements RedisManager {
    @Autowired
    private JedisPool pool;

    @Override
    public String get(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public void inc(String key, int stepLength) {
        try (Jedis jedis = pool.getResource()) {
            jedis.incrBy(key, stepLength);
        }
    }

    @Override
    public void put(String key, String value, long timeOut, TimeUnit timeUnit) {
        try (Jedis jedis = pool.getResource()) {
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            jedis.set(key, value, new SetParams().nx().px(timeUnit.toMillis(timeOut)));
        }
    }

    @Override
    public void delete(String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        }
    }

    @Override
    public void sortedListAdd(String key, Map<String, Double> doubleStringMap) {
        try (Jedis jedis = pool.getResource()) {
            jedis.zadd(key, doubleStringMap);
        }
    }

    @Override
    public void sortedListAdd(String key, String value, double score) {
        try (Jedis jedis = pool.getResource()) {
            jedis.zadd(key, score, value);
        }
    }

    @Override
    public Map<String, String> fetchAll(String prefix) {
        try (Jedis jedis = pool.getResource()) {
            Set<String> strings = jedis.keys(prefix + "*");
            Map<String, String> map     = new HashMap<>(strings.size());
            strings.forEach(i -> map.put(i, get(i)));
            return map;
        }
    }

    @Override
    public Set<Tuple> fetchRanksByPage(String listName, Pageable pageable, boolean less) {
        try (Jedis jedis = pool.getResource()) {
            long start = pageable.getOffset();
            long stop  = start + pageable.getPageSize() - 1;
            return less ?
                jedis.zrangeWithScores(listName, start, stop) :
                jedis.zrevrangeWithScores(listName, start, stop);
        }
    }

    @Override
    public Long totalElementOfList(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.zcard(key);
        }
    }

    @Override
    public Long ttl(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.ttl(key);
        }
    }

    @Override
    public Long zRank(String key, String member, boolean less) {
        try (Jedis jedis = pool.getResource()) {
            return 1 + (less ? jedis.zrank(key, member) : jedis.zrevrank(key, member));
        }
    }
}

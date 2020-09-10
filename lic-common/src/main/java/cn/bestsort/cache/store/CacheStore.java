package cn.bestsort.cache.store;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import cn.bestsort.cache.CacheStoreType;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import redis.clients.jedis.Tuple;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:28
 */

public interface CacheStore<K, V> {

    /**
     * Gets by cache key.
     *
     * @param key must not be null
     * @return cache value
     */
    V get(K key);

    /**
     * add objects to sorted list, if some exist, update them's score
     *
     * @param listName    name
     * @param scoreValMap some need to add or update
     */
    void sortedListAdd(String listName, Map<String, Double> scoreValMap);

    /**
     * add an object to sorted list, if exist, update it's score
     *
     * @param listName list's name, must be not null
     * @param value    object's JSON value
     * @param score    score, used to sort compare
     */
    void sortedListAdd(String listName, String value, double score);

    /**
     * fetch rank list by page
     *
     * @param listName    must be not null
     * @param pageable    page
     * @param lesserFirst lesser first if {true} or greater first
     * @return JSON string list
     */
    Set<Tuple> fetchRanksByPage(@NonNull String listName, Pageable pageable, boolean lesserFirst);

    /**
     * get val or return default value when val is null
     *
     * @param key          key
     * @param defaultValue default
     * @return value
     */
    V getOrElse(K key, V defaultValue);

    /**
     * get val or throw a exception when val is null
     *
     * @param key               key
     * @param exceptionSupplier exception
     * @return value
     */
    <X extends Throwable> X getOrElseThrow(K key, Supplier<? extends X> exceptionSupplier) throws Throwable;

    /**
     * Puts a cache which will be expired.
     *
     * @param key      cache key must not be null
     * @param value    cache value must not be null
     * @param timeout  the key expiration must not be less than 1
     * @param timeUnit timeout unit
     */
    @Async
    void put(@NonNull K key, @NonNull V value, long timeout, @NonNull TimeUnit timeUnit);

    /**
     * Puts a non-expired cache.
     *
     * @param key   cache key must not be null
     * @param value cache value must not be null
     */
    @Async
    void put(K key, V value);

    void put(Map<K, V> kvMap);

    /**
     * add by step length
     *
     * @param key        cache key must not be null
     * @param stepLength cache value must not be null
     */
    @Async
    void inc(@NonNull K key, int stepLength);

    Long totalElementOfList(@NonNull String key);

    /**
     * get cache interface impl support's cache type
     *
     * @return type
     */
    CacheStoreType getCacheType();

    void delete(@NonNull K key);

    void delete(Collection<K> collection);

    Map<K, V> fetchAll(String prefix);

    Long ttl(String key);

    Long zRank(String key, String member, boolean less);

    void clearCachePool();

    void init();
}
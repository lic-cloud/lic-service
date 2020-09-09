package cn.bestsort.cache.store;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import cn.bestsort.constant.MetaEnum;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 09:19
 */
public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {


    @Override
    public V get(K key) {
        return getInternal(key).orElse(null);
    }

    @Override
    public V getOrElse(K key, V defaultValue) {
        Assert.notNull(key, "Cache key must not be blank");
        return getInternal(key).orElse(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X extends Throwable> X getOrElseThrow(K key, Supplier<? extends X> exceptionSupplier) throws X {
        return (X) getInternal(key).orElseThrow(exceptionSupplier);
    }

    @Override
    public void put(@NonNull K key, @NonNull V value, long timeout, @NonNull TimeUnit timeUnit) {
        putInternal(key, value, timeout, timeUnit);
    }

    /**
     * get value
     *
     * @param key key
     * @return value in cache
     */
    public abstract Optional<V> getInternal(K key);

    @Override
    public void put(K key, V value) {
        V oldValue = get(key);
        if (oldValue != null && oldValue.equals(value)) {
            return;
        }
        putInternal(key, value,
            MetaEnum.get(Long.class, value == null ? MetaEnum.CACHE_NULL_EXPIRE : MetaEnum.CACHE_EXPIRE),
            MetaEnum.get(TimeUnit.class, MetaEnum.CACHE_UNIT));
    }

    @Override
    public void put(Map<K, V> kvMap) {
        for (K k : kvMap.keySet()) {
            put(k, kvMap.get(k));
        }
    }

}

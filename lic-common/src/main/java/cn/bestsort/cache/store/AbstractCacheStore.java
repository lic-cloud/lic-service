package cn.bestsort.cache.store;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.alibaba.fastjson.JSON;

import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.enums.KeyValEnum;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

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
    public <T> T getObj(Class<T> clazz, K key) {
        Object obj = get(key);

        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        return JSON.parseObject(obj.toString(), clazz);
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
        put(key, value, KeyValEnum.get(Long.class, LicMetaEnum.CACHE_EXPIRE),
            KeyValEnum.get(TimeUnit.class, LicMetaEnum.CACHE_UNIT));
    }

    @Override
    public void put(Map<K, V> kvMap) {
        for (K k : kvMap.keySet()) {
            put(k, kvMap.get(k));
        }
    }

}

package cn.bestsort.cache;

import java.util.concurrent.ConcurrentHashMap;

import cn.bestsort.cache.store.CacheStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:26
 */

@Slf4j
@Component
public class CacheHandler {
    private static CacheStoreType                                                STRATEGY = CacheStoreType.DEFAULT;
    private final  ConcurrentHashMap<CacheStoreType, CacheStore<String, String>> cacheMap = new ConcurrentHashMap<>();

    public void init(ApplicationContext context, CacheStoreType type) {
        for (CacheStore cacheStore : context.getBeansOfType(CacheStore.class).values()) {
            this.cacheMap.put(cacheStore.getCacheType(), cacheStore);
        }
        fetchCacheStore().init();
        setStrategy(type);
    }

    private void setStrategy(CacheStoreType strategy, boolean isChanged) {
        Assert.notNull(strategy, "storage must be not null");
        if (isChanged) {
            if (strategy.equals(CacheStoreType.DEFAULT)) {
                log.warn("检测到使用的缓存模式为[MEMORY], 为保证Lic的长期运行, 请使用REDIS模式(MEMORY只适用于短期运行)");
            }
            log.info("cache middleware was changed to {}", strategy);
            fetchCacheStore().clearCachePool();
            STRATEGY = strategy;
            fetchCacheStore().init();
        }
    }

    public String getStrategy() {
        return STRATEGY.toString();
    }

    public void setStrategy(CacheStoreType strategy) {
        setStrategy(strategy, strategy != STRATEGY);
    }

    public CacheStore<String, String> fetchCacheStore() {
        CacheStore<String, String> store = cacheMap.get(STRATEGY);
        Assert.notNull(store, "cache storage not found");
        return store;
    }
}

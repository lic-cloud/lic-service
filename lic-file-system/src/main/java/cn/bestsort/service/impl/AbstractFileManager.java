package cn.bestsort.service.impl;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.cache.store.CacheStore;
import cn.bestsort.service.FileManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 17:04
 */
public abstract class AbstractFileManager implements FileManager {
    @Autowired
    protected CacheHandler cacheHandler;
    @Autowired
    protected MetaInfoService metaInfoService;
    protected CacheStore<String, String> cache() {
        return cacheHandler.fetchCacheStore();
    }
}

package cn.bestsort.service.impl;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.cache.store.CacheStore;
import cn.bestsort.repository.FileInfoRepository;
import cn.bestsort.repository.FileMappingRepository;
import cn.bestsort.repository.FileShareRepository;
import cn.bestsort.service.FileManager;
import cn.bestsort.service.MetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 17:04
 */
public abstract class AbstractFileManager implements FileManager {
    @Autowired
    protected CacheHandler       cacheHandler;
    @Autowired
    protected MetaInfoService    metaInfoService;
    @Autowired
    protected FileInfoRepository fileInfoRepo;
    @Autowired
    protected FileShareRepository fileShareRepo;
    @Autowired
    protected FileMappingRepository fileMappingRepo;

    protected CacheStore<String, String> cache() {
        return cacheHandler.fetchCacheStore();
    }
}

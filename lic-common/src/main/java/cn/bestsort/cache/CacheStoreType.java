package cn.bestsort.cache;

import cn.bestsort.model.enums.KeyEnum;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:27
 */

public enum CacheStoreType implements KeyEnum<String> {
    /**
     * use default(db)
     */
    DEFAULT("memory_cache"),
    /**
     * use redis
     */
    REDIS("redis_server_cache");


    private final String key;
    CacheStoreType(String string) {
        this.key = string;
    }
    public static CacheStoreType parse(String val) {
        return CacheStoreType.valueOf(val);
    }

    @Override
    public String getKey() {
        return key;
    }
}


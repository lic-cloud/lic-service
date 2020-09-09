package cn.bestsort.cache;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:27
 */

public enum CacheStoreType {
    /**
     * use default(db)
     */
    DEFAULT,
    /**
     * use redis
     */
    REDIS;

    public static CacheStoreType parse(String val) {
        return CacheStoreType.valueOf(val);
    }
}


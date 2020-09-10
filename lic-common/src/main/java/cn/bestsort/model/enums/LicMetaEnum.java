package cn.bestsort.model.enums;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import cn.bestsort.cache.CacheStoreType;

/**
 * 此处存储为保证Lic系统正常运行的一些元数据,
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 08:54
 */
@SuppressWarnings("checkstyle:Indentation")
public enum LicMetaEnum implements ValueEnum<Object> {

    /**
     * 版本号
     */
    VERSION("V1.0"),

    /**
     * 是否已经进行系统的初始化
     */
    INSTALLED("false"),

    /**
     * 选择的缓存类型
     */
    CACHE_TYPE(CacheStoreType.DEFAULT.toString()),

    /**
     * 应用网址
     */
    HOST("http://localhost:8080"),


    TIME_ZERO(Timestamp.valueOf("1900-1-1 00:00:00")),

    /**
     * 默认Cache_KEY失效时间
     */
    CACHE_EXPIRE(30L),
    /**
     * val 为 null 时的缓存失效时间
     */
    CACHE_NULL_EXPIRE(5L),



    CACHE_UNIT(TimeUnit.MINUTES),

    // web 相关
    USER_SESSION(null),
    USER_IP(null),
    REQUEST_START_STAMP(null);
    private final String val;
    private final Object defaultVal;
    LicMetaEnum(Object defaultVal) {
        this.val = "lic_" + this.name().toLowerCase();
        this.defaultVal = defaultVal;
    }

    @Override
    public String getVal() {
        return this.val;
    }

    @Override
    public Object getDefault() {
        return this.defaultVal;
    }
}

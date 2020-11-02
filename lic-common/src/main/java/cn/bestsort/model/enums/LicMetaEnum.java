package cn.bestsort.model.enums;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import cn.bestsort.cache.CacheStoreType;

/**
 * 此处存储为保证Lic系统正常运行的一些元数据,[WARNING]表示强烈建议不做改动
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 08:54
 */
@SuppressWarnings("checkstyle:Indentation")
public enum LicMetaEnum implements KeyValEnum<Object>, Annotated {

    RESOURCE_DIR("resource", "[WARNING] 静态资源存储目录"),
    RESOURCE_DIR_TITLE_LENGTH(2, "[WARNING] 默认静态资源文件夹名称长度"),
    VERSION("V1.0", "版本号"),
    INIT_STATUS("step1", "是否已经进行系统的初始化"),
    CACHE_TYPE(CacheStoreType.DEFAULT.toString(), "选择的缓存类型"),
    HOST("http://localhost:8080", "Lic对外展示的URL"),
    TIME_ZERO(Timestamp.valueOf("1900-1-1 00:00:00"), "[WARNING] 当时刻为此值时表示永久"),
    CACHE_EXPIRE(30L, "缓存失效的单位时间"),
    CACHE_NULL_EXPIRE(5L, "当缓存的Val为Null时缓存的失效时间"),
    CACHE_UNIT(TimeUnit.MINUTES, "缓存失效的时间单位"),



    //=============================================================================
    // web 相关
    USER_SESSION(null),
    USER_IP(null),
    REQUEST_START_STAMP(null);
    private final String key;
    private final Object defaultVal;
    private final String annotate;
    LicMetaEnum(Object defaultVal, String annotate) {
        this.key        = "lic_" + this.name().toLowerCase();
        this.defaultVal = defaultVal;
        this.annotate   = annotate;
    }


    /**
     * 此构造字段生成的枚举不落库, 只作为缓存的Key使用
     */
    LicMetaEnum(Object defaultVal) {
        this.key        = "lic_" + this.name().toLowerCase();
        this.defaultVal = defaultVal;
        this.annotate   = null;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Object getDefault() {
        return this.defaultVal;
    }

    @Override
    public String getAnnotate() {
        return this.annotate;
    }
}

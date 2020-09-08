package cn.bestsort.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.bestsort.enums.CacheType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 存储缓存、元数据
 * 当{@link CacheType}为CACHE时候, DeleteAt表示该缓存的过期时间
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 10:17
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Cache extends BaseEntityWithDeleteAt {

    @Column
    String cacheKey;

    @Column
    String cacheVal;
}

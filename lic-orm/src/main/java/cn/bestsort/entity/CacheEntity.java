package cn.bestsort.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.bestsort.enums.CacheType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 10:17
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CacheEntity extends BaseEntity {


    @Column
    String cacheKey;

    @Column
    String val;

    @Column
    CacheType type;
}

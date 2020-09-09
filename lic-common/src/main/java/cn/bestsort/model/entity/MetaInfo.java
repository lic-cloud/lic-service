package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:14
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class MetaInfo extends BaseEntity {
    @Column(length = 36, nullable = false)
    String metaKey;

    @Column(nullable = false)
    String metaVal;
}

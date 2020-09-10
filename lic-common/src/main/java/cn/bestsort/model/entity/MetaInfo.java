package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:14
 */

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetaInfo extends BaseEntity {
    @Column(length = 64, nullable = false)
    String metaKey;

    @Column(nullable = false)
    String metaVal;

    public MetaInfo(String metaKey, String metaVal) {
        this.metaKey = metaKey;
        this.metaVal = metaVal;
    }
}

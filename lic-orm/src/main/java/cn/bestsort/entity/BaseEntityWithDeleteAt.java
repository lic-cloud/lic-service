package cn.bestsort.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import cn.bestsort.constant.MetaEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-04 09:03
 */

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class BaseEntityWithDeleteAt extends BaseEntity {
    @Column
    private Timestamp deleteAt;

    @PrePersist
    protected void prePersistDelete() {
        if (deleteAt == null) {
            deleteAt = MetaEnum.get(Timestamp.class, MetaEnum.TIME_ZERO);
        }
    }
}
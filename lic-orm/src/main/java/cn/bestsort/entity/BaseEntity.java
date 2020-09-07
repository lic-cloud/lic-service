package cn.bestsort.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-04-11 10:33
 */


@Data
@ToString
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_generator")
    @GenericGenerator(name = "id_generator", strategy = "cn.bestsort.config.JpaIdGeneratorConfig")
    private Long id;

    @Column
    @CreatedDate
    private Timestamp createAt;

    @LastModifiedDate
    @Column
    private Timestamp updateAt;
}

package cn.bestsort.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import cn.bestsort.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author bestsort
 */


@Data
@Entity
@ToString
@EqualsAndHashCode(callSuper = true)
public class MessageText extends BaseEntity {

    @Column(nullable = false,length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String message;

}

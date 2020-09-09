package cn.bestsort.entity.user;

import cn.bestsort.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@ToString
@EqualsAndHashCode(callSuper = true)
public class MessageText extends BaseEntity {

    @Column(nullable = false,length = 50)
    private String title;

    @Column(nullable = false,length = 300)
    private String message;

}

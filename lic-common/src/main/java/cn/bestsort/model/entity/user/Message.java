package cn.bestsort.model.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.bestsort.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode(callSuper = true)
public class Message extends BaseEntity {
    @Column(nullable = false)
    private int sendId;

    @Column(nullable = false)
    private int reciverId;

    @Column(nullable = false)
    private int messageId;


    @Column(nullable = false)
    private Boolean status;

}

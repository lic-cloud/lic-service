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
public class Message extends BaseEntity {
    @Column(nullable = false)
    private int sendId;

    @Column(nullable = false)
    private int recieveId;

    @Column(nullable = false)
    private int messageId;

    @Column(nullable = false)
    private Boolean status;

}

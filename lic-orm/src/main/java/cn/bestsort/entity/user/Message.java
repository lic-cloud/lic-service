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
    private int sendID;

    @Column(nullable = false)
    private int recieveID;

    @Column(nullable = false)
    private int messageID;

    @Column(nullable = false)
    private Boolean statue;

}

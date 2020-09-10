package cn.bestsort.model.entity.user;


import cn.bestsort.model.entity.BaseEntity;
import cn.bestsort.model.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.util.UUID;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 20:56
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(length = 20)
    private String userName;

    @Column(length = 50)
    private String userEmail;

    @Column(length = 100)
    private String userPassword;

    /**
     * 用户头像 默认图片地址为“xxxxx”
     */
    @Column
    private String avatar;

    /**
     *  用户状态 正常 1，停用 0
     */
    @Column
    private Status status;

    /**
     *  用户网盘容量,-1表示无穷，默认为10
     */
    @Column
    private float totalCapacity;

    /**
     *  用户已使用的容量，默认为0
     */
    @Column
    private float usedCapacity;

    /**
     * 容量单位,默认为G, 容量一律换算为KB, -1表示无穷
     */
    @Column
    private Long capacityUnit;

    /**
     * used UUID
     */
    @Column(length = 36)
    String rememberToken;

    @PrePersist
    protected void prePersist() {
        rememberToken = UUID.randomUUID().toString();
    }

}

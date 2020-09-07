package cn.bestsort.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.bestsort.entity.BaseEntity;
import cn.bestsort.enums.CapacityUnit;
import cn.bestsort.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 20:56
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(nullable = false,length = 20)
    private String userName;

    @Column(nullable = false,length = 50)
    private String userEmail;

    @Column(nullable = false,length = 36)
    private String userPassword;

    /**
     * 用户头像 默认图片地址为“xxxxx”
     */
    @Column(nullable = false)
    private String avatar;

    /**
     *  用户状态 正常 1，停用 0
     */
    @Column(nullable = false)
    private Status status;

    /**
     *  用户网盘容量,-1表示无穷，默认为10
     */
    @Column(nullable = false)
    private float totalCapacity;

    /**
     *  用户已使用的容量，默认为0
     */
    @Column(nullable = false)
    private float usedCapacity;

    /**
     * 容量单位,默认为G
     */
    @Column(nullable = false,length = 5)
    private CapacityUnit capacityUnit;
}

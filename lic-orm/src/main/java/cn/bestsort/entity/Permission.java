package cn.bestsort.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 23:28
 */
@Data
@Entity
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_generator")
    @GenericGenerator(name = "id_generator", strategy = "cn.bestsort.config.JpnIdGeneratorConfig")
    private Long permissionId;

    /**
     * 菜单名称
     */
    @Column(nullable = false,length = 50)
    private String permissionName;

    /**
     * 父菜单
     */
    @Column(nullable = false)
    private Long parentId;


    @Column(length = 30)
    private String css;

    /**
     * 跳转地址
     */
    @Column
    private String href;

    /**
     * 1：url，2：按钮
     */
    @Column
    private Integer type;

    /**
     * 权限的标识
     */
    @Column(length = 50)
    private String permission;

    /**
     * 排序
     */
    @Column
    private Long sort;

}


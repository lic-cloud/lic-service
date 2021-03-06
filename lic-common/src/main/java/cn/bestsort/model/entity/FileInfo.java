package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.bestsort.model.enums.FileNamespace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用于存储文件真实信息和实际地址
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 11:04
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo extends BaseEntity {

    @Column(length = 512)
    String path;

    @Column
    String owner;

    @Column
    String fileName;

    /**
     * 引用，有多少虚拟地址指向该文件
     */
    @Column
    Integer reference;
    /**
     * 和文件大小一起确定是否存在相同文件
     */
    @Column(length = 128)
    String md5;

    /**
     * 文件大小, 单位恒为kb， 由前端进行数值转换
     */
    @Column
    Float size;


    /**
     * 对应的文件系统实现
     */
    @Column
    FileNamespace namespace;

    /**
     * 其他元数据, 存储为JSON
     */
    @Column(length = 512)
    String extConfig;
}

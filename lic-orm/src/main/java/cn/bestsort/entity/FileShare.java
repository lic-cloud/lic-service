package cn.bestsort.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件分享， 访问地址示例如下：
 * /share/{randomKey}/rootPath/sonPath1/sonPath2
 * 针对于rootPath进行权限校验, 以兼容分享文件夹
 * @author bestsort
 * @version 1.0
 * @date 2020-09-04 09:49
 */


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class FileShare extends BaseEntity {

    @Column
    Long mappingId;

    @Column(length = 32)
    String owner;

    @Column
    Long ownerId;

    @Column(length = 32)
    String password;

    @Column
    String url;

    @Column
    Timestamp endTime;

}

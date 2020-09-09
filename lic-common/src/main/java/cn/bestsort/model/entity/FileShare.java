package cn.bestsort.model.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class FileShare extends BaseEntity {

    @Column
    Long mappingId;

    @Column(length = 32)
    String owner;

    @Column
    Long ownerId;

    @Column(length = 32)
    String password;

    /**
     * 此处的URL只存储中间的随机字段, 如:
     * https://{host}/share/{randomKey}/{path} 中的{randomKey}
     */
    @Column(length = 16)
    String url;

    @Column
    Timestamp expire;

}

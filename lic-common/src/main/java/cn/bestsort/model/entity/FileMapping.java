package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.bestsort.model.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用于存储文件虚拟地址，根据虚拟地址映射至具体的文件
 * @author bestsort
 * @version 1.0
 * @date 2020-09-04 09:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class FileMapping extends BaseEntityWithDeleteAt {

    @Column
    String fileName;

    /**
     * 文件真实记录所对应的id
     */
    @Column
    Long infoId;

    Long ownerId;

    /**
     * 文件大小, 统一用KB存储，由前端进行转换
     * 如果为目录则大小为目录下所有文件之和
     */
    @Column
    Float size;

    /**
     * 父级目录对应id
     */
    @Column
    Long pid;

    @Column
    Boolean isDir;

    /**
     * 该文件是否已经被分享
     */
    @Column
    Integer share;

    @Column
    Status status;
}

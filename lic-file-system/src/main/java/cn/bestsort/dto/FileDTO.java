package cn.bestsort.dto;

import cn.bestsort.enums.FileNamespace;
import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 16:29
 */
@Data
public class FileDTO {
    /**
     * 用户的用户名
     */
    private String username;

    /**
     * 命名空间，用以确定对应的文件系统
     */
    private FileNamespace namespace;
    /**
     * 文件路径
     */
    private String dir;
}

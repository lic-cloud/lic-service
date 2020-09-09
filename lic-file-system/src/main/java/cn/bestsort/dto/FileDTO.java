package cn.bestsort.dto;

import cn.bestsort.model.FileInfo;
import cn.bestsort.enums.FileNamespace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    private Long userId;
    /**
     * 命名空间，用以确定对应的文件系统
     */
    private FileNamespace namespace;


    /**
     * 文件信息
     */
    private FileInfo fileInfo;

    /**
     * 文件夹信息(部分场景需要)
     */
    private FileInfo dirInfo;

}

package cn.bestsort.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/10/27 18:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Upload {
    /**
     * 分段的文件
     */
    String data;
    /**
     * 文件名称
     */
    String name;
    /**
     * 文件的总片数
     */
    Integer total;
    /**
     * 当前片数
     */
    Integer index;
    /**
     * 文件的md5
     */
    String md5;
    /**
     * 文件的总大小
     */
    String size;
    /**
     * 当前切片的文件大小
     */
    String chunksize;
    /**
     * 文件的后缀名
     */
    String suffix;
}

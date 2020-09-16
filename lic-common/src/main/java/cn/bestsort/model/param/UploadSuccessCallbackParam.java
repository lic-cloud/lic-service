package cn.bestsort.model.param;

import cn.bestsort.model.enums.FileNamespace;
import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:56
 */
@Data
public class UploadSuccessCallbackParam {
    Float size;
    String name;
    Long pid;
    String md5;
    FileNamespace namespace;
}

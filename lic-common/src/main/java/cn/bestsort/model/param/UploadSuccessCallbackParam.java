package cn.bestsort.model.param;

import cn.bestsort.model.enums.FileNamespace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadSuccessCallbackParam {
    Float size;
    String name;
    Long pid;
    String md5;
    FileNamespace namespace;
}

package cn.bestsort.model.param;

import cn.bestsort.model.enums.FileNamespace;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
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
@ApiModel
public class UploadSuccessCallbackParam {
    @ApiParam("文件大小，单位为KB")
    Float size;
    @ApiParam("文件名")
    String name;

    Long pid;
    String md5;
    FileNamespace namespace;
    Boolean finished;

    /**
     * 实际文件名
     */
    String entityName;
}

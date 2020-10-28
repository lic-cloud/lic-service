package cn.bestsort.model.param;

import java.sql.Timestamp;

import cn.bestsort.model.enums.LicMetaEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 19:57
 */

@Data
@ApiModel
public class ShareParam {

    @ApiParam("对应文件映射id, 可为文件夹/文件")
    Long mappingId;


    @ApiParam("密码, 可为空")
    String password;

    /**
     * P.S:永久在数据库中表示为{@link LicMetaEnum#TIME_ZERO}
     */
    @ApiParam("过期时间，当不传输此字段的时候表示永久， 传输值为一个时间戳")
    Timestamp expire;

    @ApiParam("为空时表示创建，不为空时表示更新对应分享的设置")
    String url;

}

package cn.bestsort.model.param;

import java.sql.Timestamp;

import cn.bestsort.constant.MetaEnum;
import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 19:57
 */

@Data
public class ShareParam {

    /**
     * 文件id
     */
    Long fileId;

    /**
     * 密码, 可为空
     */
    String password;

    /**
     * 过期时间， 当时间为 null 的时候表示永久
     * P.S:永久在数据库中表示为{@link MetaEnum#TIME_ZERO}
     */
    Timestamp expire;

}

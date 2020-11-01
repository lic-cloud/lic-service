package cn.bestsort.model.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class ResponseInfo implements Serializable {

    private Integer code;
    private String message;

    public ResponseInfo() {}

    public static ResponseInfo of(Integer code, String msg) {
        ResponseInfo info = new ResponseInfo();
        info.code = code;
        info.message = msg;
        return info;
    }

    public static ResponseInfo of(HttpStatus code, String msg) {
        ResponseInfo info = new ResponseInfo();
        info.code = code.value();
        info.message = msg;
        return info;
    }

}

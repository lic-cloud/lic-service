package utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void responseJson(HttpServletResponse response, int status, Object data) {
        //status http的状态码
        //data token(对象)
        try {
            //跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            //能否为前端返回token看下面的代码
            response.setContentType("application/json;charset=UTF-8");
            //http的状态码
            response.setStatus(status);
            //data转换为json返回到前端
            response.getWriter().write(JSONObject.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

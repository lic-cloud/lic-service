package cn.bestsort.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-12 10:20
 */
public class UrlUtil {

    /**
     * 向url链接追加参数
     * @param url
     * @param params Map<String, String>
     * @return
     */
    public static String appendParams(String url, Map<String, String> params) {
        if (StringUtils.isBlank(url)) {
            return "";
        } else if (MapUtils.isEmpty(params)) {
            return url.trim();
        } else {
            StringBuilder sb = new StringBuilder("");
            Set<String> keys = params.keySet();
            for (String key : keys) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);

            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            if (index > -1) {
                //url说明有问号
                if ((length - 1) == index) {
                    //url最后一个符号为？，如：http://wwww.baidu.com?
                    url += sb.toString();
                } else {
                    //情况为：http://wwww.baidu.com?aa=11
                    url += "&" + sb.toString();
                }
            } else {
                //url后面没有问号，如：http://wwww.baidu.com
                url += "?" + sb.toString();
            }
            return url;
        }
    }

    /**
     * 向url链接追加参数(单个)
     * @param url
     * @param name String
     * @param value String
     * @return
     */
    public static String appendParam(String url, String name, String value) {
        if (StringUtils.isBlank(url)) {
            return "";
        } else if (StringUtils.isBlank(name)) {
            return url.trim();
        } else {
            Map<String, String> params = new HashMap<>();
            params.put(name, value);
            return appendParams(url, params);
        }
    }

    /**
     * 移除url链接的多个参数
     * @param url String
     * @param paramNames String[]
     * @return
     */
    public static String removeParams(String url, String... paramNames) {
        if (StringUtils.isBlank(url)) {
            return "";
        } else if (ArrayUtils.isEmpty(paramNames)) {
            return url.trim();
        } else {
            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            if (index > -1) {
                //url说明有问号
                if ((length - 1) == index) {
                    //url最后一个符号为？，如：http://wwww.baidu.com?
                    return url;
                } else {
                    //情况为：http://wwww.baidu.com?aa=11或http://wwww.baidu.com?aa=或http://wwww.baidu.com?aa
                    String baseUrl = url.substring(0, index);
                    String paramsString = url.substring(index + 1);
                    String[] params = paramsString.split("&");
                    if (!ArrayUtils.isEmpty(params)) {
                        Map<String, String> paramsMap = new HashMap<String, String>();
                        for (String param : params) {
                            if (!StringUtils.isBlank(param)) {
                                String[] oneParam = param.split("=");
                                String paramName = oneParam[0];
                                int count = 0;
                                for (String name : paramNames) {
                                    if (name.equals(paramName)) {
                                        break;
                                    }
                                    count++;
                                }
                                if (count == paramNames.length) {
                                    paramsMap.put(paramName, (oneParam.length > 1) ?
                                        oneParam[1] : "");
                                }
                            }
                        }
                        if (!MapUtils.isEmpty(paramsMap)) {
                            StringBuilder paramBuffer = new StringBuilder(baseUrl);
                            paramBuffer.append("?");
                            Set<String> set = paramsMap.keySet();
                            for (String paramName : set) {
                                paramBuffer.append(paramName).append("=").append(paramsMap.get(paramName)).append("&");
                            }
                            paramBuffer.deleteCharAt(paramBuffer.length() - 1);
                            return paramBuffer.toString();
                        }
                        return baseUrl;
                    }
                }
            }
            return url;
        }
    }
}

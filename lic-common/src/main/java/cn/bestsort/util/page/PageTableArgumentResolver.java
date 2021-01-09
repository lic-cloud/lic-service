package cn.bestsort.util.page;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.bestsort.util.PageUtil;
import com.google.common.collect.Maps;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 分页、查询参数解析
 *
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public class PageTableArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 如果请求接收的参数为PageTableRequest就进入到resolveArgument这个方法。
     * 将HttpServletRequest转化为PageTableRequest。
     * 再加一个配置。
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(PageTableRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String start = "start";
        String length = "length";
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        PageTableRequest tableRequest = new PageTableRequest();
        Map<String, String[]> param = request.getParameterMap();
        if (param.containsKey(start)) {
            tableRequest.setOffset(Integer.parseInt(request.getParameter("start")));
        }
        if (param.containsKey(length)) {
            tableRequest.setLimit(Integer.parseInt(request.getParameter("length")));
        }
        Map<String, Object> map = Maps.newHashMap();
        param.forEach((k, v) -> {
            if (v.length == 1) {
                map.put(k, v[0]);
            } else {
                map.put(k, Arrays.asList(v));
            }
        });
        tableRequest.setParams(map);
        PageUtil.setOrderBy(tableRequest, map);
        PageUtil.removeParam(tableRequest.getParams());
        return tableRequest;
    }
}

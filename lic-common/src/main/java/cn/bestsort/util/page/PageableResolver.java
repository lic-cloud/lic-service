package cn.bestsort.util.page;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.bestsort.util.PageUtil;
import com.google.common.collect.Maps;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-16 16:27
 */
public class PageableResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String[]> param = request.getParameterMap();
        Map<String, Object> map = Maps.newHashMap();
        param.forEach((k, v) -> map.put(k, v.length == 1 ? v[0] : Arrays.asList(v)));
        String start = request.getParameter("start");
        if (start == null) {
            start = "0";
        }
        String length = request.getParameter("length");
        if (length == null) {
            length = "20";
        }
        return PageUtil.toPageable(
            Integer.parseInt(start),
            Integer.parseInt(length), map);
    }
}

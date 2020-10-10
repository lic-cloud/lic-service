package cn.bestsort.util.page;

import java.util.List;
import java.util.Map;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-10 16:40
 */
public interface Listable<T> {
    int count(Map<String, Object> params);
    List<T> list(Map<String, Object> params, int offset, int limit);
}

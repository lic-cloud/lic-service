package cn.bestsort.util;

import java.util.Iterator;
import java.util.Map;

import cn.bestsort.util.page.PageTableRequest;
import cn.bestsort.util.page.PageTableResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-16 16:24
 */
public class PageUtil {
    public static void removeParam(Map<String, Object> map) {
        String leftBracket = "[";
        String rightBracket = "]";
        String underLine = "_";
        Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Object> entry = itr.next();
            String key = entry.getKey();
            if (key.contains(leftBracket) || key.contains(rightBracket)) {
                itr.remove();
                continue;
            }
            if (key.equals(underLine)) {
                itr.remove();
            }
        }
    }

    /**
     * 从datatables分页请求数据中解析排序
     */

    public static void setOrderBy(PageTableRequest tableRequest, Map<String, Object> map) {
        StringBuilder orderBy = new StringBuilder();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            String index = (String) map.get("order[" + i + "][column]");
            if (StringUtils.isEmpty(index)) {
                break;
            }
            String column = (String) map.get("columns[" + index + "][data]");
            if (StringUtils.isBlank(column)) {
                continue;
            }
            String sort = (String) map.get("order[" + i + "][dir]");

            orderBy.append(column).append(" ").append(sort).append(", ");
        }

        if (orderBy.length() > 0) {
            tableRequest.getParams().put("orderBy",
                                         " order by " + StringUtils.substringBeforeLast(orderBy.toString(), ","));
        }
    }
    public static Pageable toPageable(int start, int length) {
        return PageRequest.of(start / length, length, Sort.unsorted());
    }
    public static Pageable toPageable(int start, int length, Map<String, Object> oderBy) {
        return PageRequest.of(start / length, length, fetchSort(oderBy));
    }
    public static Sort fetchSort(Map<String, Object> param) {
        Sort sortObj = Sort.unsorted();
        int size = param.size();
        for (int i = 0; i < size; i++) {
            String index = (String) param.get("order[" + i + "][column]");
            if (StringUtils.isEmpty(index)) {
                break;
            }
            String column = (String) param.get("columns[" + index + "][data]");
            if (StringUtils.isBlank(column)) {
                continue;
            }
            String sort = (String) param.get("order[" + i + "][dir]");
            if (sort != null) {
                sortObj = sortObj.and(Sort.by(Direction.fromString(sort), column));
            }
        }
        return sortObj;
    }
    public static PageTableResponse toPageTable(Page page) {
        //TODO 筛选后统计
        return new PageTableResponse(
            (int)page.getTotalElements(),
            (int)page.getTotalElements(),
            page.getContent());
    }
}


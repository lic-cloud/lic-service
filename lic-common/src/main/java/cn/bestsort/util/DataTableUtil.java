package cn.bestsort.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-18 13:55
 */
public class DataTableUtil {
    public static Pageable toPageable(int start,int length) {
        return PageRequest.of(start / length, length, Sort.unsorted());
    }
}

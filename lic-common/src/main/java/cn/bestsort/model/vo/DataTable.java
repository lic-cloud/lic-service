package cn.bestsort.model.vo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-18 14:05
 */
@Getter
@Builder
public class DataTable<T> {

    private int draw;
    private int start;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;

    public static <T> DataTable<T> build(Page<T> page, int draw, int start) {
        return DataTable.<T>builder()
            .data(page.getContent())
            .recordsTotal(page.getTotalElements())
            .recordsFiltered(page.getTotalElements())
            .draw(draw)
            .start(start)
            .build();
    }
}
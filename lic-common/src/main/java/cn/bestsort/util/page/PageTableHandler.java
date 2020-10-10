package cn.bestsort.util.page;

import java.util.ArrayList;
import java.util.List;

/** 分页查询处理器
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public class PageTableHandler {
    private final CountHandler countHandler;
    private final ListHandler listHandler;
    private OrderHandler orderHandler;

    public static PageTableResponse handlePage(PageTableRequest request, Listable listable) {
        return new PageTableHandler(
            tableRequest -> listable.count(tableRequest.getParams()),
            tableRequest -> listable.list(tableRequest.getParams(),
                                                 tableRequest.getOffset(),
                                                 tableRequest.getLimit()))
            .handle(request);
    }
    public PageTableHandler(CountHandler countHandler, ListHandler listHandler) {
        super();
        this.countHandler = countHandler;
        this.listHandler = listHandler;
    }


    public PageTableHandler(CountHandler countHandler, ListHandler listHandler, OrderHandler orderHandler) {
        this(countHandler, listHandler);
        this.orderHandler = orderHandler;
    }

    public PageTableResponse handle(PageTableRequest dtRequest) {
        int count = 0;
        List<?> list = null;

        count = this.countHandler.count(dtRequest);
        if (count > 0) {
            if (orderHandler != null) {
                dtRequest = orderHandler.order(dtRequest);
            }
            list = this.listHandler.list(dtRequest);
        }

        if (list == null) {
            list = new ArrayList<>();
        }

        return new PageTableResponse(count, count, list);
    }

    public interface ListHandler {
        List<?> list(PageTableRequest request);
    }

    public interface CountHandler {
        int count(PageTableRequest request);
    }

    public interface OrderHandler {
        PageTableRequest order(PageTableRequest request);
    }
}

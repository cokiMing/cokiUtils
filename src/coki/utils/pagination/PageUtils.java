package coki.utils.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 * Created by wuyiming on 2017/10/19.
 */
public class PageUtils {

    private PageUtils() {

    }

    public static List pagination(List list, Page page) {
        return pagination(list, page.getPageNo(), page.getPageSize());
    }

    /**
     * 逻辑分页
     *
     * @param list     待分页的数组
     * @param pageNo   当前页码
     * @param pageSize 每页记录数
     * @return 分页后的数组
     */
    public static List pagination(List list, int pageNo, int pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        int offset = pageSize * (pageNo - 1);

        if (offset + pageSize < list.size()) {
            return list.subList(offset, offset + pageSize);
        } else if (offset < list.size()) {
            return list.subList(offset, list.size());
        } else {
            return new ArrayList();
        }
    }
}

package domain.common.paging;

import java.util.List;

public class PagedResult<T> {

    private final List<T> list;
    private final int pageSize;
    private final int pagesCount;
    private final int totalPages;

    public PagedResult(List<T> list, int pagesCount, int pageSize, int totalPages) {
        this.list = list;
        this.pageSize = pageSize;
        this.pagesCount = pagesCount;
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPagesCount() {
        return pagesCount;
    }
}
package coki.utils.pagination;

/**
 * 分页类
 * Created by wuyiming on 2017/10/19.
 */
public final class Page {

    //当前页码
    private int pageNo;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //总记录数
    private long totalRecord;

    public Page() {

    }

    public Page(int pageNo, int pageSize, long totalRecord) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.totalPage = (int)totalRecord/pageSize;
    }

    public Page(Builder builder) {
        pageNo= builder._pageNo;
        pageSize= builder._pageSize;
        totalPage= builder._totalPage;
        totalRecord= builder._totalRecord;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public static class Builder {
        private int _pageNo;
        private int _pageSize;
        private int _totalPage;
        private long _totalRecord;
        public  Builder() {}
        public  Builder pageNo(int pageNo) { _pageNo=pageNo; return this;}
        public  Builder pageSize(int pageSize) { _pageSize=pageSize; return this;}
        public  Builder totalPage(int totalPage) { _totalPage=totalPage; return this;}
        public  Builder totalRecord(long totalRecord) { _totalRecord=totalRecord; return this;}
        public Page build() {return new Page(this);}
    }
}

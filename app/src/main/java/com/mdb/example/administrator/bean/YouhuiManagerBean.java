package com.mdb.example.administrator.bean;

import java.util.List;

/**
 * 查看优惠券界面
 */
public class YouhuiManagerBean {

    /**
     * data : {"pageInfo":{"endRow":4,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"publish_type":2,"item":100,"freegoods":"","create_time":"2018-10-30 15:12:39","num":100,"endtime":"2018-10-31 10:09:00","term":30,"starttime":"2018-10-30 10:09:00","coupon_type":1,"isallow":1},{"publish_type":2,"item":9,"freegoods":"","create_time":"2018-10-30 15:21:06","num":100,"endtime":"2018-10-31 10:09:00","term":0,"starttime":"2018-10-30 10:09:00","coupon_type":2},{"publish_type":2,"item":10,"freegoods":"","create_time":"2018-10-30 16:01:37","num":100,"endtime":"2018-10-31 10:09:00","isallow":1,"starttime":"2018-10-30 10:09:00","coupon_type":3},{"publish_type":2,"item":100,"freegoods":"111","create_time":"2018-10-30 16:18:01","num":100,"endtime":"2018-10-31 10:09:00","starttime":"2018-10-30 10:09:00","coupon_type":4}],"navigatePages":1,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":4,"startRow":1,"total":4}}
     * message : 请求成功
     * state : 1
     */

    private DataBean data;
    private String message;
    private int state;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * pageInfo : {"endRow":4,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"publish_type":2,"item":100,"freegoods":"","create_time":"2018-10-30 15:12:39","num":100,"endtime":"2018-10-31 10:09:00","term":30,"starttime":"2018-10-30 10:09:00","coupon_type":1,"isallow":1},{"publish_type":2,"item":9,"freegoods":"","create_time":"2018-10-30 15:21:06","num":100,"endtime":"2018-10-31 10:09:00","term":0,"starttime":"2018-10-30 10:09:00","coupon_type":2},{"publish_type":2,"item":10,"freegoods":"","create_time":"2018-10-30 16:01:37","num":100,"endtime":"2018-10-31 10:09:00","isallow":1,"starttime":"2018-10-30 10:09:00","coupon_type":3},{"publish_type":2,"item":100,"freegoods":"111","create_time":"2018-10-30 16:18:01","num":100,"endtime":"2018-10-31 10:09:00","starttime":"2018-10-30 10:09:00","coupon_type":4}],"navigatePages":1,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":4,"startRow":1,"total":4}
         */

        private PageInfoBean pageInfo;

        public PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public static class PageInfoBean {
            /**
             * endRow : 4
             * firstPage : 1
             * hasNextPage : false
             * hasPreviousPage : false
             * isFirstPage : true
             * isLastPage : true
             * lastPage : 1
             * list : [{"publish_type":2,"item":100,"freegoods":"","create_time":"2018-10-30 15:12:39","num":100,"endtime":"2018-10-31 10:09:00","term":30,"starttime":"2018-10-30 10:09:00","coupon_type":1},{"publish_type":2,"item":9,"freegoods":"","create_time":"2018-10-30 15:21:06","num":100,"endtime":"2018-10-31 10:09:00","term":0,"starttime":"2018-10-30 10:09:00","coupon_type":2},{"publish_type":2,"item":10,"freegoods":"","create_time":"2018-10-30 16:01:37","num":100,"endtime":"2018-10-31 10:09:00","isallow":1,"starttime":"2018-10-30 10:09:00","coupon_type":3},{"publish_type":2,"item":100,"freegoods":"111","create_time":"2018-10-30 16:18:01","num":100,"endtime":"2018-10-31 10:09:00","starttime":"2018-10-30 10:09:00","coupon_type":4}]
             * navigatePages : 1
             * navigatepageNums : [1]
             * nextPage : 0
             * pageNum : 1
             * pageSize : 10
             * pages : 1
             * prePage : 0
             * size : 4
             * startRow : 1
             * total : 4
             */

            private int endRow;
            private int firstPage;
            private boolean hasNextPage;
            private boolean hasPreviousPage;
            private boolean isFirstPage;
            private boolean isLastPage;
            private int lastPage;
            private int navigatePages;
            private int nextPage;
            private int pageNum;
            private int pageSize;
            private int pages;
            private int prePage;
            private int size;
            private int startRow;
            private int total;
            private List<ListBean> list;
            private List<Integer> navigatepageNums;

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public int getFirstPage() {
                return firstPage;
            }

            public void setFirstPage(int firstPage) {
                this.firstPage = firstPage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public boolean isHasPreviousPage() {
                return hasPreviousPage;
            }

            public void setHasPreviousPage(boolean hasPreviousPage) {
                this.hasPreviousPage = hasPreviousPage;
            }

            public boolean isIsFirstPage() {
                return isFirstPage;
            }

            public void setIsFirstPage(boolean isFirstPage) {
                this.isFirstPage = isFirstPage;
            }

            public boolean isIsLastPage() {
                return isLastPage;
            }

            public void setIsLastPage(boolean isLastPage) {
                this.isLastPage = isLastPage;
            }

            public int getLastPage() {
                return lastPage;
            }

            public void setLastPage(int lastPage) {
                this.lastPage = lastPage;
            }

            public int getNavigatePages() {
                return navigatePages;
            }

            public void setNavigatePages(int navigatePages) {
                this.navigatePages = navigatePages;
            }

            public int getNextPage() {
                return nextPage;
            }

            public void setNextPage(int nextPage) {
                this.nextPage = nextPage;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public int getPrePage() {
                return prePage;
            }

            public void setPrePage(int prePage) {
                this.prePage = prePage;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public List<Integer> getNavigatepageNums() {
                return navigatepageNums;
            }

            public void setNavigatepageNums(List<Integer> navigatepageNums) {
                this.navigatepageNums = navigatepageNums;
            }

            public static class ListBean {
                /**
                 * publish_type : 2
                 * item : 100.0
                 * freegoods :
                 * create_time : 2018-10-30 15:12:39
                 * num : 100
                 * endtime : 2018-10-31 10:09:00
                 * term : 30.0
                 * starttime : 2018-10-30 10:09:00
                 * coupon_type : 1
                 * isallow : 1
                 */

                private int publish_type;
                private double item;
                private String freegoods;
                private String create_time;
                private int num;
                private String endtime;
                private double term;
                private String starttime;
                private int coupon_type;
                private int isallow;

                public int getPublish_type() {
                    return publish_type;
                }

                public void setPublish_type(int publish_type) {
                    this.publish_type = publish_type;
                }

                public double getItem() {
                    return item;
                }

                public void setItem(double item) {
                    this.item = item;
                }

                public String getFreegoods() {
                    return freegoods;
                }

                public void setFreegoods(String freegoods) {
                    this.freegoods = freegoods;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getEndtime() {
                    return endtime;
                }

                public void setEndtime(String endtime) {
                    this.endtime = endtime;
                }

                public double getTerm() {
                    return term;
                }

                public void setTerm(double term) {
                    this.term = term;
                }

                public String getStarttime() {
                    return starttime;
                }

                public void setStarttime(String starttime) {
                    this.starttime = starttime;
                }

                public int getCoupon_type() {
                    return coupon_type;
                }

                public void setCoupon_type(int coupon_type) {
                    this.coupon_type = coupon_type;
                }

                public int getIsallow() {
                    return isallow;
                }

                public void setIsallow(int isallow) {
                    this.isallow = isallow;
                }
            }
        }
    }
}

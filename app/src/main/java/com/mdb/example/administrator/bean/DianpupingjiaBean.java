package com.mdb.example.administrator.bean;

import java.util.List;

/**
 * 店铺评价
 */
public class DianpupingjiaBean {


    /**
     * data : {"pageInfo":{"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"comment_a":2,"comment_img":"2","shop_code":"7m48axLp","comment_code":"2","uuid":"1000001431536168","comment_r":2,"modifyTime":"2018-10-16 10:23:18","comment_des":"2","user_id":5,"createTime":"2018-10-18 14:05:34","comment_t":2,"service_id":20,"commentId":16,"status":1}],"navigatePages":1,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}}
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
         * pageInfo : {"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"comment_a":2,"comment_img":"2","shop_code":"7m48axLp","comment_code":"2","uuid":"1000001431536168","comment_r":2,"modifyTime":"2018-10-16 10:23:18","comment_des":"2","user_id":5,"createTime":"2018-10-18 14:05:34","comment_t":2,"service_id":20,"commentId":16,"status":1}],"navigatePages":1,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}
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
             * endRow : 1
             * firstPage : 1
             * hasNextPage : false
             * hasPreviousPage : false
             * isFirstPage : true
             * isLastPage : true
             * lastPage : 1
             * list : [{"comment_a":2,"comment_img":"2","shop_code":"7m48axLp","comment_code":"2","uuid":"1000001431536168","comment_r":2,"modifyTime":"2018-10-16 10:23:18","comment_des":"2","user_id":5,"createTime":"2018-10-18 14:05:34","comment_t":2,"service_id":20,"commentId":16,"status":1}]
             * navigatePages : 1
             * navigatepageNums : [1]
             * nextPage : 0
             * pageNum : 1
             * pageSize : 10
             * pages : 1
             * prePage : 0
             * size : 1
             * startRow : 1
             * total : 1
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
                 * comment_a : 2
                 * comment_img : 2
                 * shop_code : 7m48axLp
                 * comment_code : 2
                 * uuid : 1000001431536168
                 * comment_r : 2
                 * modifyTime : 2018-10-16 10:23:18
                 * comment_des : 2
                 * user_id : 5
                 * createTime : 2018-10-18 14:05:34
                 * comment_t : 2
                 * service_id : 20
                 * commentId : 16
                 * status : 1
                 */

                private int comment_a;
                private String comment_img;
                private String shop_code;
                private String comment_code;
                private String uuid;
                private int comment_r;
                private String modifyTime;
                private String comment_des;
                private int user_id;
                private String createTime;
                private int comment_t;
                private int service_id;
                private int commentId;
                private int status;

                public int getComment_a() {
                    return comment_a;
                }

                public void setComment_a(int comment_a) {
                    this.comment_a = comment_a;
                }

                public String getComment_img() {
                    return comment_img;
                }

                public void setComment_img(String comment_img) {
                    this.comment_img = comment_img;
                }

                public String getShop_code() {
                    return shop_code;
                }

                public void setShop_code(String shop_code) {
                    this.shop_code = shop_code;
                }

                public String getComment_code() {
                    return comment_code;
                }

                public void setComment_code(String comment_code) {
                    this.comment_code = comment_code;
                }

                public String getUuid() {
                    return uuid;
                }

                public void setUuid(String uuid) {
                    this.uuid = uuid;
                }

                public int getComment_r() {
                    return comment_r;
                }

                public void setComment_r(int comment_r) {
                    this.comment_r = comment_r;
                }

                public String getModifyTime() {
                    return modifyTime;
                }

                public void setModifyTime(String modifyTime) {
                    this.modifyTime = modifyTime;
                }

                public String getComment_des() {
                    return comment_des;
                }

                public void setComment_des(String comment_des) {
                    this.comment_des = comment_des;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public int getComment_t() {
                    return comment_t;
                }

                public void setComment_t(int comment_t) {
                    this.comment_t = comment_t;
                }

                public int getService_id() {
                    return service_id;
                }

                public void setService_id(int service_id) {
                    this.service_id = service_id;
                }

                public int getCommentId() {
                    return commentId;
                }

                public void setCommentId(int commentId) {
                    this.commentId = commentId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }
    }
}

package com.mdb.example.administrator.bean;

/**
 * 叫号首页展示
 */
public class JiaohaoShowBean {

    /**
     * data : {"num":2,"currentNo":3,"id":6}
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
         * num : 2
         * currentNo : 3
         * id : 6
         */

        private int num;
        private String currentNo;
        private int id;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCurrentNo() {
            return currentNo;
        }

        public void setCurrentNo(String currentNo) {
            this.currentNo = currentNo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

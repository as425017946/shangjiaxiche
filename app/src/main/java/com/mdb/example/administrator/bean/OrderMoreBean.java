package com.mdb.example.administrator.bean;

import java.util.List;

/**
 * 订单详情使用
 */
public class OrderMoreBean {

    /**
     * data : [{"orderTime":"2018-10-19 16:40:00","money":20,"createTime":"2018-10-17 15:21:00","service_name":"洗车","coupon_money":2,"type":"3","uuid":"1","status":1}]
     * message : 请求成功
     * state : 1
     */

    private String message;
    private int state;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderTime : 2018-10-19 16:40:00
         * money : 20.0
         * createTime : 2018-10-17 15:21:00
         * service_name : 洗车
         * coupon_money : 2.0
         * type : 3
         * uuid : 1
         * status : 1
         * tel：1892062
         */

        private String orderTime;
        private double money;
        private String createTime;
        private String service_name;
        private double coupon_money;
        private String type;
        private String uuid;
        private int status;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        private String tel;

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public double getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(double coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

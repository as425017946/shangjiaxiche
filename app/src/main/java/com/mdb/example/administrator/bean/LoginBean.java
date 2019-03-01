package com.mdb.example.administrator.bean;

/**
 * 登录界面使用
 */
public class LoginBean {

    /**
     * data : {"reason":"真费劲","shop_code":"6KAFG8ry","businessline":"123564","mobile":"18920629331","shopId":6,"shop_name":"科技","status":1}
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
         * shop_code : 6KAFG8ry
         * businessline : 123564
         * mobile : 18920629331
         * shopId : 6
         * shop_name : 科技
         * status : 1
         */

        private String shop_code;
        private String businessline;
        private String mobile;
        private int shopId;
        private String shop_name;
        private String status;
        private String reason;

        public String getWaitnum_flag() {
            return waitnum_flag;
        }

        public void setWaitnum_flag(String waitnum_flag) {
            this.waitnum_flag = waitnum_flag;
        }

        private String waitnum_flag;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getShop_code() {
            return shop_code;
        }

        public void setShop_code(String shop_code) {
            this.shop_code = shop_code;
        }

        public String getBusinessline() {
            return businessline;
        }

        public void setBusinessline(String businessline) {
            this.businessline = businessline;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

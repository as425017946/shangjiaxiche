package com.mdb.example.administrator.bean;

/**
 * 查询店铺信息使用
 */
public class ShopInfoBean {

    /**
     * data : {"start_time":"09:00","businessline":"1","end_time":"18:00","tel":"18920629331","shop_logo":"user-herder1541042873.jpg"}
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
         * start_time : 09:00
         * businessline : 1
         * end_time : 18:00
         * tel : 18920629331
         * shop_logo : user-herder1541042873.jpg
         */

        private String start_time;
        private String businessline;
        private String end_time;
        private String tel;
        private String shop_logo;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getBusinessline() {
            return businessline;
        }

        public void setBusinessline(String businessline) {
            this.businessline = businessline;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }
    }
}

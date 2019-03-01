package com.mdb.example.administrator.bean;

/**
 * 查询商家资质
 */
public class ShopZizhiBean {

    /**
     * data : {"manager_name":"张","owner_name":"孟","mail":"45286435@qq.com","company_img":"user-herder1541042873.jpg,","shop_addr":"来咯","owner_phone":"18920629331","shop_name":"测"}
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
         * manager_name : 张
         * owner_name : 孟
         * mail : 45286435@qq.com
         * company_img : user-herder1541042873.jpg,
         * shop_addr : 来咯
         * owner_phone : 18920629331
         * shop_name : 测
         */

        private String manager_name;
        private String owner_name;
        private String mail;
        private String company_img;
        private String shop_addr;
        private String owner_phone;
        private String shop_name;

        public String getManager_name() {
            return manager_name;
        }

        public void setManager_name(String manager_name) {
            this.manager_name = manager_name;
        }

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getCompany_img() {
            return company_img;
        }

        public void setCompany_img(String company_img) {
            this.company_img = company_img;
        }

        public String getShop_addr() {
            return shop_addr;
        }

        public void setShop_addr(String shop_addr) {
            this.shop_addr = shop_addr;
        }

        public String getOwner_phone() {
            return owner_phone;
        }

        public void setOwner_phone(String owner_phone) {
            this.owner_phone = owner_phone;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }
    }
}

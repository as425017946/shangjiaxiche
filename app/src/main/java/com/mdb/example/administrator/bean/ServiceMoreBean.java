package com.mdb.example.administrator.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 商家服务详情
 */
public class ServiceMoreBean {

    /**
     * data : {"isonlineorder":1,"service_name":"普洗","service_num":10,"endtime":"2018-10-30 20:00:00","service_descript":"普洗测试","starttime":"2018-10-30 09:00:00","service_img":"1541082734969,","interval_time":5,"servicesDetail":[{"type_name":"小型","price":30,"detail":"测试","id":22,"car_type":1,"status":1}],"status":1,"switch":0}
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
         * isonlineorder : 1
         * service_name : 普洗
         * service_num : 10
         * endtime : 2018-10-30 20:00:00
         * service_descript : 普洗测试
         * starttime : 2018-10-30 09:00:00
         * service_img : 1541082734969,
         * interval_time : 5
         * servicesDetail : [{"type_name":"小型","price":30,"detail":"测试","id":22,"car_type":1,"status":1}]
         * status : 1
         * switch : 0
         */

        private int isonlineorder;
        private String service_name;
        private String service_num;
        private String endtime;
        private String service_descript;
        private String starttime;
        private String service_img;
        private String interval_time;
        private int status;


        @SerializedName("switch")
        private int switchX;
        private List<ServicesDetailBean> servicesDetail;

        public int getIsonlineorder() {
            return isonlineorder;
        }

        public void setIsonlineorder(int isonlineorder) {
            this.isonlineorder = isonlineorder;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getService_num() {
            return service_num;
        }

        public void setService_num(String service_num) {
            this.service_num = service_num;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getService_descript() {
            return service_descript;
        }

        public void setService_descript(String service_descript) {
            this.service_descript = service_descript;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getService_img() {
            return service_img;
        }

        public void setService_img(String service_img) {
            this.service_img = service_img;
        }

        public String getInterval_time() {
            return interval_time;
        }

        public void setInterval_time(String interval_time) {
            this.interval_time = interval_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSwitchX() {
            return switchX;
        }

        public void setSwitchX(int switchX) {
            this.switchX = switchX;
        }

        public List<ServicesDetailBean> getServicesDetail() {
            return servicesDetail;
        }

        public void setServicesDetail(List<ServicesDetailBean> servicesDetail) {
            this.servicesDetail = servicesDetail;
        }

        public static class ServicesDetailBean {
            /**
             * type_name : 小型
             * price : 30.0
             * detail : 测试
             * id : 22
             * car_type : 1
             * status : 1
             */

            private String type_name;
            private double price;
            private String detail;
            private int id;
            private int car_type;
            private int status;

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCar_type() {
                return car_type;
            }

            public void setCar_type(int car_type) {
                this.car_type = car_type;
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


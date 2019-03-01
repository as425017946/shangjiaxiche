package com.mdb.example.administrator.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServersStatusBean {


    /**
     * data : [{"isonlineorder":1,"service_name":"普洗","service_id":19,"switch":0,"status":1},{"isonlineorder":0,"service_name":"精洗","service_id":20,"switch":0,"status":1},{"isonlineorder":0,"service_name":"打蜡","service_id":26,"switch":0,"status":1}]
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
         * isonlineorder : 1
         * service_name : 普洗
         * service_id : 19
         * switch : 0
         * status : 1
         */

        private int isonlineorder;
        private String service_name;
        private int service_id;
        @SerializedName("switch")
        private int switchX;
        private int status;

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

        public int getService_id() {
            return service_id;
        }

        public void setService_id(int service_id) {
            this.service_id = service_id;
        }

        public int getSwitchX() {
            return switchX;
        }

        public void setSwitchX(int switchX) {
            this.switchX = switchX;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

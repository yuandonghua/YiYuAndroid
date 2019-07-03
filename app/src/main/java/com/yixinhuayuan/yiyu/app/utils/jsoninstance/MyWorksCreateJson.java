package com.yixinhuayuan.yiyu.app.utils.jsoninstance;

public class MyWorksCreateJson {

    /**
     * status : true
     * code : 200
     * message : SUCCESS
     * data : {"id":21,"user_id":22,"class_name":"千岁","number":0,"image_url":"https://yy363626256-1258529412.cos.ap-beijing.myqcloud.com/image/t4YU3Hc2HqYAuIIxMoTgMonjMa7lHXhRzU35T0Lf.jpeg"}
     */

    private boolean status;
    private int code;
    private String message;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 21
         * user_id : 22
         * class_name : 千岁
         * number : 0
         * image_url : https://yy363626256-1258529412.cos.ap-beijing.myqcloud.com/image/t4YU3Hc2HqYAuIIxMoTgMonjMa7lHXhRzU35T0Lf.jpeg
         */

        private int id;
        private int user_id;
        private String class_name;
        private int number;
        private String image_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}

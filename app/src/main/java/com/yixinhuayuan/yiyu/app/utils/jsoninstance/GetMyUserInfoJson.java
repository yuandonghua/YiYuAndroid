package com.yixinhuayuan.yiyu.app.utils.jsoninstance;

/**
 * 请求自己服务器过去用户数据接口成功后,解析返回的Json数据
 */
public class GetMyUserInfoJson {


    /**
     * status : true
     * code : 200
     * message : SUCCESS
     * data : {"id":14,"user_id":1,"account":"zhangjinyu666661","type":1,"status":1}
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
         * id : 14
         * user_id : 1
         * account : zhangjinyu666661
         * type : 1
         * status : 1
         */

        private int id;
        private int user_id;
        private String account;
        private int type;
        private int status;

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

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

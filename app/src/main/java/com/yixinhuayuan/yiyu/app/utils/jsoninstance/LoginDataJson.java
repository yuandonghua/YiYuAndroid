package com.yixinhuayuan.yiyu.app.utils.jsoninstance;

/**
 * 请求自己服务器注册接口成功后,用于解析返回的Json数据
 */
public class LoginDataJson {
    /**
     * status : true
     * code : 200
     * message : SUCCESS
     * data : {"id":7,"user_id":20,"account":"zhangjinyu3","type":1,"status":1,"nickname":"提将","photo":"https://yy363626256-1258529412.cos.ap-beijing.myqcloud.com/image/o1pUkbbNmB7bhDAAk4MT6rTVZb6bybHiNW5KY86R.jpeg","fans":0,"star":0,"sex":1,"introduce":"","art_beat":0}
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
         * id : 7
         * user_id : 20
         * account : zhangjinyu3
         * type : 1
         * status : 1
         * nickname : 提将
         * photo : https://yy363626256-1258529412.cos.ap-beijing.myqcloud.com/image/o1pUkbbNmB7bhDAAk4MT6rTVZb6bybHiNW5KY86R.jpeg
         * fans : 0
         * star : 0
         * sex : 1
         * introduce :
         * art_beat : 0
         */

        private int id;
        private int user_id;
        private String account;
        private int type;
        private int status;
        private String nickname;
        private String photo;
        private int fans;
        private int star;
        private int sex;
        private String introduce;
        private int art_beat;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public int getArt_beat() {
            return art_beat;
        }

        public void setArt_beat(int art_beat) {
            this.art_beat = art_beat;
        }
    }

}

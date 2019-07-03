package com.yixinhuayuan.yiyu.app.utils.data;

public class MyWorksData {
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

    @Override
    public String toString() {

        return "class_name: " + class_name + " ,number: " + number + ", image_url: " + image_url + " ,id: " + id + " ,user_id: " + user_id;
    }
}

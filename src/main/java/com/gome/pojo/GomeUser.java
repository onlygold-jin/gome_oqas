package com.gome.pojo;

public class GomeUser {
    private Integer userId;

    private String userName;

    private String userPass;

    private String userStatu;

    private String userPersonsName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass == null ? null : userPass.trim();
    }

    public String getUserStatu() {
        return userStatu;
    }

    public void setUserStatu(String userStatu) {
        this.userStatu = userStatu == null ? null : userStatu.trim();
    }

    public String getUserPersonsName() {
        return userPersonsName;
    }

    public void setUserPersonsName(String userPersonsName) {
        this.userPersonsName = userPersonsName == null ? null : userPersonsName.trim();
    }
}
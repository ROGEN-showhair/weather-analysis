package com.jd.analysis.bean;

/**
 * Created by xudi1 on 2017/3/8.
 */
public class UserBean {
    private String UserId;
    private String PassWord;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "UserId='" + UserId + '\'' +
                ", PassWord='" + PassWord + '\'' +
                '}';
    }
}

package com.example.lagomfurniture.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("response")
    private String response;

    @SerializedName("id")
    private int id;

    @SerializedName("user_email")
    private String userEmail;

    private String password;

    @SerializedName("platform")
    private String platform;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("profile_Image")
    private String profileImage;

    @SerializedName("resultcode")
    private int resultcode;

    // 생성자
    public User() {
    }

    // 로그인
    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    // 회원가입
    public User(String userEmail, String password, String nickname) {
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String userEmail, String password, String platform, String nickname, String profileImage) {
        this.userEmail = userEmail;
        this.password = password;
        this.platform = platform;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public boolean matchResultCode(int newResultCode) {
        if (newResultCode == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "response='" + response + '\'' + ", id=" + id + ", userEmail='" + userEmail + '\'' + ", password='" + password + '\'' + ", platform='" + platform + '\'' + ", nickname='" + nickname + '\'' + ", profileImage='" + profileImage + '\'' + ", resultcode=" + resultcode + '}';
    }
}
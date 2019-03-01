package com.itsol.smartoffice.dto;

import java.io.Serializable;

public class UserDto implements Serializable {
    private Integer userId;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private Boolean status;
    private String linkImage;

    public UserDto(Integer userId, String userName, String fullName, String email, String phone, Boolean status, String linkImage) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.linkImage = linkImage;
    }

    public UserDto() {
    }

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
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}

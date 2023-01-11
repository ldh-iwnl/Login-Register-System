package com.mayikt.entity;

public class UserEntity {
    /**
     *  user id
     */
    private Long id;
    /**
     * phone number
     */
    private String phone;
    /**
     * user password
     */
    private String pwd;

    public UserEntity(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }

    public UserEntity(Long id, String phone, String pwd) {
        this.id = id;
        this.phone = phone;
        this.pwd = pwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

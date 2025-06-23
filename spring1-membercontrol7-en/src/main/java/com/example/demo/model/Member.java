package com.example.demo.model;

public class Member {
    private Long id;
    private String account;
    private String passwd;
    private String realname;

    // Default constructor
    public Member() {}

    // Parameterized constructor
    public Member(Long id, String account, String passwd, String realname) {
        this.id = id;
        this.account = account;
        this.passwd = passwd;
        this.realname = realname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
} 
package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Response {
    private int error;      // 0 means success, non-zero means error
    private String mesg;    // Error or success message
    private int insertId;   // ID returned by insert operation
    private Object data;    // Data returned by query operation

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
    }

    public int getInsertId() {
        return insertId;
    }

    public void setInsertId(int insertId) {
        this.insertId = insertId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
} 
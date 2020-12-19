package com.example.trafficapp;

public class POJO_user {
    public String status;
    public String inputEmail;
    public String id;

    public POJO_user(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public POJO_user(String status, String inputEmail, String id) {
        this.inputEmail = inputEmail;
        this.status = status;
        this.id= id;
    }

}

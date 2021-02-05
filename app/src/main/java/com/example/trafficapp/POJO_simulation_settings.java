package com.example.trafficapp;

public class POJO_simulation_settings {
    public String greenTime;
    public String orangeTime;
    public String additionTime;


    public POJO_simulation_settings() {

    }

    public POJO_simulation_settings(String greenTime, String orangeTime, String additionTime) {
        this.greenTime = greenTime;
        this.orangeTime = orangeTime;
        this.additionTime = additionTime;
    }


    public String getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(String greenTime) {
        this.greenTime = greenTime;
    }

    public String getOrangeTime() {
        return orangeTime;
    }

    public void setOrangeTime(String orangeTime) {
        this.orangeTime = orangeTime;
    }

    public String getAdditionTime() {
        return additionTime;
    }

    public void setAdditionTime(String additionTime) {
        this.additionTime = additionTime;
    }


}

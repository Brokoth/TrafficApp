package com.example.trafficapp;

public class POJO_simulation_settings {
    public String redTime;
    public String greenTime;
    public String orangeTime;
    public String additionTime;
    public String subtractionTime;
    public String settingsType;

    public POJO_simulation_settings(){

    }

    public POJO_simulation_settings(String redTime, String greenTime, String orangeTime, String additionTime, String subtractionTime, String settingsType) {
        this.redTime = redTime;
        this.greenTime = greenTime;
        this.orangeTime = orangeTime;
        this.additionTime = additionTime;
        this.subtractionTime = subtractionTime;
        this.settingsType = settingsType;
    }

    public String getRedTime() {
        return redTime;
    }

    public void setRedTime(String redTime) {
        this.redTime = redTime;
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

    public String getSubtractionTime() {
        return subtractionTime;
    }

    public void setSubtractionTime(String subtractionTime) {
        this.subtractionTime = subtractionTime;
    }

    public String getSettingsType() {
        return settingsType;
    }

    public void setSettingsType(String settingsType) {
        this.settingsType = settingsType;
    }
}

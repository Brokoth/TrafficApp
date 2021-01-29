package com.example.trafficapp;

public class POJO_simulation {
    public String userID;
    public String junctionType;
    public String densityType;
    public String redTime;
    public String greenTime;
    public String orangeTime;
    public String additionTime;
    public String subtractionTime;
    public String link_one;
    public String link_two;
    public String link_three;
    public String link_four;
    public String frame_interval;

    public POJO_simulation() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getJunctionType() {
        return junctionType;
    }

    public void setJunctionType(String junctionType) {
        this.junctionType = junctionType;
    }

    public String getDensityType() {
        return densityType;
    }

    public void setDensityType(String densityType) {
        this.densityType = densityType;
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

    public String getLink_one() {
        return link_one;
    }

    public void setLink_one(String link_one) {
        this.link_one = link_one;
    }

    public String getLink_two() {
        return link_two;
    }

    public void setLink_two(String link_two) {
        this.link_two = link_two;
    }

    public String getLink_three() {
        return link_three;
    }

    public void setLink_three(String link_three) {
        this.link_three = link_three;
    }

    public String getLink_four() {
        return link_four;
    }

    public void setLink_four(String link_four) {
        this.link_four = link_four;
    }

    public String getFrame_interval() {
        return frame_interval;
    }

    public void setFrame_interval(String frame_interval) {
        this.frame_interval = frame_interval;
    }

    public POJO_simulation(String userID, String junctionType, String densityType, String additionTime, String greenTime, String orangeTime, String redTime, String subtractionTime, String link_one, String link_two, String link_three, String link_four, String frame_interval) {
        this.userID = userID;
        this.junctionType = junctionType;
        this.densityType = densityType;
        this.redTime = redTime;
        this.greenTime = greenTime;
        this.orangeTime = orangeTime;
        this.additionTime = additionTime;
        this.subtractionTime = subtractionTime;
        this.link_one = link_one;
        this.link_two = link_two;
        this.link_three = link_three;
        this.link_four = link_four;
        this.frame_interval = frame_interval;
    }
}

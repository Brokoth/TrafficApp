package com.example.trafficapp;

public class POJO_simulation {
    public String userID;
    public String junctionType;
    public String densityType;
    public String greenTime;
    public String orangeTime;
    public String additionTime;
    public String link_one;
    public String link_two;
    public String link_three;
    public String link_four;
    public String frame_interval;
    public String start_time;
    public String end_time;
    public String timemilli;
    public String ai_efficiency;
    public String control_efficiency;
    public String ai_coords;
    public String con_coords;

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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTimemilli() {
        return timemilli;
    }

    public void setTimemilli(String timemilli) {
        this.timemilli = timemilli;
    }

    public String getAi_efficiency() {
        return ai_efficiency;
    }

    public void setAi_efficiency(String ai_efficiency) {
        this.ai_efficiency = ai_efficiency;
    }

    public String getControl_efficiency() {
        return control_efficiency;
    }

    public void setControl_efficiency(String control_efficiency) {
        this.control_efficiency = control_efficiency;
    }

    public String getAi_density_change_rate_coords() {
        return ai_coords;
    }

    public void setAi_density_change_rate_coords(String ai_coords) {
        this.ai_coords = ai_coords;
    }

    public String getCon_density_change_rate_coords() {
        return con_coords;
    }

    public void setCon_density_change_rate_coords(String con_coords) {
        this.con_coords = con_coords;
    }

    public POJO_simulation(String userID, String junctionType, String densityType, String greenTime, String orangeTime, String additionTime, String link_one, String link_two, String link_three, String link_four, String frame_interval, String start_time, String end_time, String timemilli, String ai_efficiency, String control_efficiency, String ai_coords, String con_coords) {
        this.userID = userID;
        this.junctionType = junctionType;
        this.densityType = densityType;
        this.greenTime = greenTime;
        this.orangeTime = orangeTime;
        this.additionTime = additionTime;
        this.link_one = link_one;
        this.link_two = link_two;
        this.link_three = link_three;
        this.link_four = link_four;
        this.frame_interval = frame_interval;
        this.start_time = start_time;
        this.end_time = end_time;
        this.timemilli = timemilli;
        this.ai_efficiency = ai_efficiency;
        this.control_efficiency = control_efficiency;
        this.ai_coords = ai_coords;
        this.con_coords = con_coords;
    }
}

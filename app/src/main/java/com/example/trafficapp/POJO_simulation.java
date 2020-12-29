package com.example.trafficapp;

public class POJO_simulation {
    public String userID;
    public String junctionType;
    public String densityType;
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

    public POJO_simulation(String userID, String junctionType, String densityType) {
        this.userID = userID;
        this.junctionType = junctionType;
        this.densityType = densityType;
    }
}

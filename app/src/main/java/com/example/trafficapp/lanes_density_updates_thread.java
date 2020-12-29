package com.example.trafficapp;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.TextView;

public class lanes_density_updates_thread extends Thread {
    int seconds;
    public Handler handler;
    private String densityType;
    private String junctionType;
    private TextView lane_1, lane_2, lane_3, lane_4;

    lanes_density_updates_thread(int seconds, String densityType, String junctionType, TextView lane_1, TextView lane_2, TextView lane_3) {
        this.seconds = seconds;
        this.densityType = densityType;
        this.junctionType = junctionType;
        this.lane_1 = lane_1;
        this.lane_2 = lane_2;
        this.lane_3 = lane_3;
    }

    lanes_density_updates_thread(int seconds, String densityType, String junctionType, TextView lane_1, TextView lane_2, TextView lane_3, TextView lane_4) {
        this.seconds = seconds;
        this.densityType = densityType;
        this.junctionType = junctionType;
        this.lane_1 = lane_1;
        this.lane_2 = lane_2;
        this.lane_3 = lane_3;
        this.lane_4 = lane_4;
    }

    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        seconds = seconds * 1000;
        if (junctionType.equals("T junction")){
            if (densityType.equals("Heavy Traffic")) {
                while (SimulationActivity.running) {

                }
            } else if (densityType.equals("Light Traffic")) {
                while (SimulationActivity.running) {

                }
            } else {
                while (SimulationActivity.running) {

                }
            }
        }
        else {
            if (densityType.equals("Heavy Traffic")) {
                while (SimulationActivity.running) {

                }
            } else if (densityType.equals("Light Traffic")) {
                while (SimulationActivity.running) {

                }
            } else {
                while (SimulationActivity.running) {

                }
            }
        }

    }
}

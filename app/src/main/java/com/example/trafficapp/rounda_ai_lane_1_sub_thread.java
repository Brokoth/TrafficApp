package com.example.trafficapp;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class rounda_ai_lane_1_sub_thread extends Thread {
    int seconds;
    public Handler handler;
    private String TAG = "rounda_ai_lane_1_sub_thread";

    rounda_ai_lane_1_sub_thread() {

    }

    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        seconds = 1000;
        while (SimulationActivity.running) {
            if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")&&Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString())>0){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int lane_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString());
                        lane_val = lane_val - 1;
                        SimulationActivity.roundabout_ai_lane_1_in.setText(String.valueOf(lane_val));
                    }
                });
                SystemClock.sleep(seconds);
            }
        }
    }
}

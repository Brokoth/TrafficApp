package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.TextView;

public class ai_traffic_sequencing_thread extends Thread {
    private String junctionType;
    private Context c;
    private TextView light_1, light_2, light_3, light_4;

    ai_traffic_sequencing_thread(String junctionType, Context c, TextView light_1, TextView light_2, TextView light_3) {
        this.junctionType = junctionType;
        this.c = c;
        this.light_1 = light_1;
        this.light_2 = light_2;
        this.light_3 = light_3;
    }
    ai_traffic_sequencing_thread(String junctionType, Context c, TextView light_1, TextView light_2, TextView light_3,TextView light_4) {
        this.junctionType = junctionType;
        this.c = c;
        this.light_1 = light_1;
        this.light_2 = light_2;
        this.light_3 = light_3;
        this.light_4 = light_4;
    }

    @Override
    public void run() {
        if (junctionType.equals("T junction")) {
        }
    }
}

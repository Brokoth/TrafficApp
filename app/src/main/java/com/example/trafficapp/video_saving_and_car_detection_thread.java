package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class video_saving_and_car_detection_thread extends Thread {
    private String frame_interval, link1, link2, link3, link4, path;
    private Context c;
    public Handler handler;

    video_saving_and_car_detection_thread(String frame_interval, String link1, String link2, String link3, String link4, Context c, String path) {
        this.frame_interval = frame_interval;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.link4 = link4;
        this.c = c;
        this.path = path;
    }


    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        int count = 0;
        while (count == 0) {
            if (!Python.isStarted())
                Python.start(new AndroidPlatform(c));
            Python python = Python.getInstance();
            PyObject pyObj = python.getModule("video_to_density_converter_script");
            PyObject traffic_values = pyObj.callAttr("vid_saving", link1, link2, link3, link4, frame_interval, path);
            Log.e("traffic values", "run: " + traffic_values.toString());
            count = count + 1;
        }
    }
}


package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.lang.reflect.Array;
import java.util.Random;

public class car_detection_thread extends Thread {
    private String frame_interval, link1, link2, link3, link4;
    private Context c;
    public Handler handler;

    car_detection_thread(String frame_interval, String link1, String link2, String link3, String link4, Context c) {
        this.frame_interval = frame_interval;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.link4 = link4;
        this.c = c;
    }


    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        if (!Python.isStarted())
            Python.start(new AndroidPlatform(c));
        Python python = Python.getInstance();
        PyObject pyObj = python.getModule("traffic_light_python_script");
        PyObject lane1_val = pyObj.callAttr("car_detection",link1,frame_interval);
        PyObject lane2_val = pyObj.callAttr("car_detection",link2,frame_interval);
        PyObject lane3_val = pyObj.callAttr("car_detection",link3,frame_interval);
        PyObject lane4_val = pyObj.callAttr("car_detection",link4,frame_interval);

    }
}


package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ai_traffic_sequencing_thread extends Thread {
    private String junctionType;
    private Context c;
    private int seconds;
    public Handler handler;

    ai_traffic_sequencing_thread(int seconds, String junctionType, Context c) {
        this.junctionType = junctionType;
        this.c = c;
        this.seconds = seconds;

    }


    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        seconds = seconds * 1000;
        int new_green = 0;
        if (!Python.isStarted())
            Python.start(new AndroidPlatform(c));
        Python python = Python.getInstance();
        final PyObject pyObj = python.getModule("traffic_light_python_script");
        PyObject main = pyObj.callAttr("main");
        if (junctionType.equals("T junction")) {
            while (SimulationActivity.running) {
                int lane1_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString());
                int lane2_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString());
                int lane3_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString());
                PyObject t_junction = pyObj.callAttr("t_junction", lane1_val, lane2_val, lane3_val);
                int length = Array.getLength(t_junction);
                for (int i = 0; i < length; i++) {
                    if (String.valueOf(Array.get(t_junction, i)) == "0") {
                        new_green = i + 1;
                        break;
                    }
                }
                if (new_green != 0) {
                    if (SimulationActivity.tjunction_ai_light_1.getText().toString().equals("green")) {
                        if (new_green != 1) {
                            if (new_green == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_1.setText("orange");
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(3000);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_1.setText("red");
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_2.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_1.setText("orange");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(3000);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_1.setText("red");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_3.setText("green");
                                    }
                                });
                            }
                        }
                    } else if (SimulationActivity.tjunction_ai_light_2.getText().toString().equals("green")) {
                        if (new_green != 2) {
                            if (new_green == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(3000);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_2.setText("red");
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_1.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(3000);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_2.setText("red");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_3.setText("green");
                                    }
                                });
                            }
                        }
                    } else {
                        if (new_green != 3) {
                            if (new_green == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_3.setText("orange");
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(3000);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_3.setText("red");
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_2.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_3.setText("orange");
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(3000);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_3.setText("red");
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_1.setText("green");
                                    }
                                });
                            }
                        }
                    }
                }
                else {
                    Random rand = new Random();
                    int rand_no = rand.nextInt(3)+1;
                    
                }
            }
        }
    }
}

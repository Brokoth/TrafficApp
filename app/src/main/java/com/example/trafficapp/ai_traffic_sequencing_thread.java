package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
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
    private int orangetime;
    public Handler handler;

    ai_traffic_sequencing_thread(int orangetime, String junctionType, Context c) {
        this.junctionType = junctionType;
        this.c = c;
        this.orangetime = orangetime;

    }


    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        int seconds = 10000;
        int new_green = 0;
        if (!Python.isStarted())
            Python.start(new AndroidPlatform(c));
        Python python = Python.getInstance();
        PyObject pyObj = python.getModule("traffic_light_python_script");
        pyObj.callAttr("main");
        if (junctionType.equals("T junction")) {
            while (SimulationActivity.running) {
                int lane1_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString());
                int lane2_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString());
                int lane3_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString());
               PyObject t_junction = pyObj.callAttr("t_junction", lane1_val, lane2_val, lane3_val);
                char[] result =t_junction.toString().replaceAll("[\\[\\]]","").toCharArray();
                int length = Array.getLength(result);
                for (int i = 0; i < length; i++) {
                    if (String.valueOf(Array.get(result, i)) == "0") {
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
                                SystemClock.sleep(orangetime);
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
                                SystemClock.sleep(orangetime);
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
                                SystemClock.sleep(orangetime);
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
                                SystemClock.sleep(orangetime);
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
                    } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
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
                                SystemClock.sleep(orangetime);
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
                                SystemClock.sleep(orangetime);
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
                    } else {
                        if (new_green == 1) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.tjunction_ai_light_1.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.tjunction_ai_light_1.setText("green");
                                }
                            });
                        } else if (new_green == 2) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.tjunction_ai_light_2.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
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
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.tjunction_ai_light_3.setText("green");
                                }
                            });
                        }
                    }
                }
                else {
                    Random rand = new Random();
                    int rand_no = rand.nextInt(3) + 1;
                    if (SimulationActivity.tjunction_ai_light_1.getText().toString().equals("green")) {
                        if (rand_no != 1) {
                            if (rand_no == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_1.setText("orange");
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
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
                                SystemClock.sleep(orangetime);
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
                        if (rand_no != 2) {
                            if (rand_no == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                        SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
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
                                SystemClock.sleep(orangetime);
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
                    } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
                        if (rand_no != 3) {
                            if (rand_no == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_3.setText("orange");
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
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
                                SystemClock.sleep(orangetime);
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
                    } else {
                        if (rand_no == 1) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.tjunction_ai_light_1.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.tjunction_ai_light_1.setText("green");
                                }
                            });
                        } else if (rand_no == 2) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.tjunction_ai_light_2.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
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
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.tjunction_ai_light_3.setText("green");
                                }
                            });
                        }
                    }
                }
                SystemClock.sleep(seconds);
            }
        } else {
            while (SimulationActivity.running) {
                int lane1_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString());
                int lane2_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString());
                int lane3_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString());
                int lane4_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString());
                PyObject t_junction = pyObj.callAttr("roundabout", lane1_val, lane2_val, lane3_val, lane4_val);
                char[] result =t_junction.toString().replaceAll("[\\[\\]]","").toCharArray();
                int length = Array.getLength(result);
                for (int i = 0; i < length; i++) {
                    if (String.valueOf(Array.get(result, i)) == "0") {
                        new_green = i + 1;
                        break;
                    }
                }
                if (new_green != 0) {
                    if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")) {
                        if (new_green != 1) {
                            if (new_green == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_1.setText("red");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_2.setText("green");
                                    }
                                });
                            } else if (new_green == 3) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_1.setText("red");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_3.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_1.setText("red");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_4.setText("green");
                                    }
                                });
                            }
                        }
                    } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
                        if (new_green != 2) {
                            if (new_green == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_2.setText("red");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_1.setText("green");
                                    }
                                });
                            } else if (new_green == 3) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_2.setText("red");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_3.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_2.setText("red");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_4.setText("green");
                                    }
                                });
                            }
                        }
                    } else if (SimulationActivity.roundabout_ai_light_3.getText().toString().equals("green")) {
                        if (new_green != 3) {
                            if (new_green == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_3.setText("red");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_2.setText("green");
                                    }
                                });
                            } else if (new_green == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_3.setText("red");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_1.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_3.setText("red");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_4.setText("green");
                                    }
                                });
                            }
                        }
                    } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
                        if (new_green != 4) {
                            if (new_green == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_4.setText("red");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_2.setText("green");
                                    }
                                });
                            } else if (new_green == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_4.setText("red");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_1.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_4.setText("red");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_3.setText("green");
                                    }
                                });
                            }
                        }
                    } else {
                        if (new_green == 1) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_1.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_1.setText("green");
                                }
                            });
                        } else if (new_green == 2) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_2.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_2.setText("green");
                                }
                            });
                        } else if (new_green == 3) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_3.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_3.setText("green");
                                }
                            });
                        }
                        else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_4.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_4.setText("green");
                                }
                            });
                        }
                    }
                } else {
                    Random rand = new Random();
                    int rand_no = rand.nextInt(3) + 1;
                    if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")) {
                        if (rand_no != 1) {
                            if (rand_no == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_1.setText("red");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_2.setText("green");
                                    }
                                });
                            } else if (rand_no == 3) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_1.setText("red");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_3.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_1.setText("red");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_4.setText("green");
                                    }
                                });
                            }
                        }
                    } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
                        if (rand_no != 2) {
                            if (rand_no == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_2.setText("red");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_1.setText("green");
                                    }
                                });
                            } else if (rand_no == 3) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_2.setText("orange");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.tjunction_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.tjunction_ai_light_2.setText("red");
                                        SimulationActivity.tjunction_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.tjunction_ai_light_3.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_2.setText("red");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_4.setText("green");
                                    }
                                });
                            }
                        }
                    } else if (SimulationActivity.roundabout_ai_light_3.getText().toString().equals("green")) {
                        if (rand_no != 3) {
                            if (rand_no == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_3.setText("red");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_2.setText("green");
                                    }
                                });
                            } else if (rand_no == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_3.setText("red");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_1.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_3.setText("red");
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_4.setText("green");
                                    }
                                });
                            }
                        }
                    } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
                        if (rand_no != 4) {
                            if (rand_no == 2) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_2.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_4.setText("red");
                                        SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_2.setText("green");
                                    }
                                });
                            } else if (rand_no == 1) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_1.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_4.setText("red");
                                        SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_1.setText("green");
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_4.setText("orange");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                        SimulationActivity.roundabout_ai_light_3.setText("orange");
                                    }
                                });
                                SystemClock.sleep(orangetime);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_red);
                                        SimulationActivity.roundabout_ai_light_4.setText("red");
                                        SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                        SimulationActivity.roundabout_ai_light_3.setText("green");
                                    }
                                });
                            }
                        }
                    } else {
                        if (rand_no == 1) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_1.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_1.setText("green");
                                }
                            });
                        } else if (rand_no == 2) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_2.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_2.setText("green");
                                }
                            });
                        } else if (rand_no == 3) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_3.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_3.setText("green");
                                }
                            });
                        }
                        else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_4.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_4.setText("green");
                                }
                            });
                        }
                    }
                }
                SystemClock.sleep(seconds);
            }
        }
    }
}

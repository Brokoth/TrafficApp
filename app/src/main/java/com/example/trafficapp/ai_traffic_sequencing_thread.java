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
        orangetime = orangetime * 1000;
        int new_green = 0;
        if (!Python.isStarted())
            Python.start(new AndroidPlatform(c));
        Python python = Python.getInstance();
        PyObject pyObj = python.getModule("traffic_light_python_script");
        // pyObj.callAttr("main");
        int max_light_gap = 3;
        final int[] light_one_count = {0};
        final int[] light_two_count = {0};
        final int[] light_three_count = {0};
        final int[] light_four_count = {0};
        if (junctionType.equals("T junction")) {
            while (SimulationActivity.running) {
                int lane1_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString());
                int lane2_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString());
                int lane3_val = Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString());
                if ((light_one_count[0] - light_two_count[0]) > max_light_gap || (light_one_count[0] - light_three_count[0]) > max_light_gap || (light_two_count[0] - light_one_count[0]) > max_light_gap
                        || (light_two_count[0] - light_three_count[0]) > max_light_gap || (light_three_count[0] - light_one_count[0]) > max_light_gap
                        || (light_three_count[0] - light_two_count[0]) > max_light_gap) {
                    if (light_one_count[0] < light_two_count[0] && light_one_count[0] < light_three_count[0]) {
                        if (SimulationActivity.tjunction_ai_light_1.getText().toString().equals("green")) {
                            light_one_count[0]++;
                        } else if (SimulationActivity.tjunction_ai_light_2.getText().toString().equals("green")) {
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
                                    light_one_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
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
                                    light_one_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else {
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
                                    light_one_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        }
                    } else if (light_two_count[0] < light_three_count[0]) {
                        if (SimulationActivity.tjunction_ai_light_1.getText().toString().equals("green")) {
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
                                    light_two_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else if (SimulationActivity.tjunction_ai_light_2.getText().toString().equals("green")) {
                            light_two_count[0]++;
                        } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
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
                                    SimulationActivity.tjunction_ai_light_2.setText("red");
                                    SimulationActivity.tjunction_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.tjunction_ai_light_2.setText("green");
                                    light_two_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else {
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
                                    light_two_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        }
                    } else {
                        if (SimulationActivity.tjunction_ai_light_1.getText().toString().equals("green")) {
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
                                    light_three_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else if (SimulationActivity.tjunction_ai_light_2.getText().toString().equals("green")) {
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
                                    light_three_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
                            light_three_count[0]++;

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
                                    light_three_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        }
                    }
                } else {

                    PyObject t_junction = pyObj.callAttr("t_junction", lane1_val, lane2_val, lane3_val, c.getFilesDir().toString());
                    Log.d("tjun ai: ", t_junction.toString());
                    char[] result = t_junction.toString().replaceAll("[\\[\\]]", "").toCharArray();
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
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.tjunction_ai_light_2.getText().toString().equals("green")) {
                            if (new_green != 2) {
                                if (new_green == 1) {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
                            if (new_green != 3) {
                                if (new_green == 2) {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else {
                            if (new_green == 1) {
                                if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_one_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else if (new_green == 2) {
                                if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_two_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else {
                                if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_three_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            }
                        }
                    } else {
                        Random rand = new Random();
                        int rand_no = rand.nextInt(3) + 1;
                        if (SimulationActivity.tjunction_ai_light_1.getText().toString().equals("green")) {
                            if (rand_no != 1) {
                                if (rand_no == 2) {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.tjunction_ai_light_2.getText().toString().equals("green")) {
                            if (rand_no != 2) {
                                if (rand_no == 1) {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
                            if (rand_no != 3) {
                                if (rand_no == 2) {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else {
                            if (rand_no == 1) {
                                if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_1_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_one_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else if (rand_no == 2) {
                                if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_2_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_two_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else {
                                if (Integer.parseInt(SimulationActivity.tjunction_ai_lane_3_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_three_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            }
                        }
                    }
                    // SystemClock.sleep(seconds);
                }
            }
        } else {
            while (SimulationActivity.running) {

                if ((light_one_count[0] - light_two_count[0]) > max_light_gap || (light_one_count[0] - light_three_count[0]) > max_light_gap || (light_one_count[0] - light_four_count[0]) > max_light_gap
                        || (light_two_count[0] - light_one_count[0]) > max_light_gap || (light_two_count[0] - light_three_count[0]) > max_light_gap || (light_two_count[0] - light_four_count[0]) > max_light_gap
                        || (light_three_count[0] - light_one_count[0]) > max_light_gap || (light_three_count[0] - light_two_count[0]) > max_light_gap || (light_three_count[0] - light_four_count[0]) > max_light_gap
                        || (light_four_count[0] - light_one_count[0]) > max_light_gap || (light_four_count[0] - light_two_count[0]) > max_light_gap || (light_four_count[0] - light_three_count[0]) > max_light_gap) {
                    if (light_one_count[0] < light_two_count[0] && light_one_count[0] < light_three_count[0] && light_one_count[0] < light_four_count[0]) {
                        if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")) {
                            light_one_count[0]++;
                        } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
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
                                    light_one_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else if (SimulationActivity.roundabout_ai_light_3.getText().toString().equals("green")) {
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
                                    light_one_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
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
                                    light_one_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else {
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
                                    light_one_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        }
                    } else if (light_two_count[0] < light_three_count[0] && light_two_count[0] < light_four_count[0]) {
                        if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")) {
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
                                    light_two_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
                            light_two_count[0]++;
                        } else if (SimulationActivity.roundabout_ai_light_3.getText().toString().equals("green")) {
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
                                    SimulationActivity.roundabout_ai_light_2.setText("red");
                                    SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_2.setText("green");
                                    light_two_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
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
                                    light_two_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else {
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
                                    light_two_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        }
                    } else if (light_three_count[0] < light_four_count[0]) {
                        if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")) {
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
                                    light_three_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_2.setText("orange");
                                    SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                    SimulationActivity.roundabout_ai_light_3.setText("orange");
                                }
                            });
                            SystemClock.sleep(orangetime);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                    SimulationActivity.roundabout_ai_light_2.setText("red");
                                    SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                    SimulationActivity.roundabout_ai_light_3.setText("green");
                                    light_three_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else if (SimulationActivity.tjunction_ai_light_3.getText().toString().equals("green")) {
                            light_three_count[0]++;

                        } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
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
                                    light_three_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else {
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
                                    light_three_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        }
                    } else {
                        if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")) {
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
                                    light_four_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
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
                                    light_four_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else if (SimulationActivity.roundabout_ai_light_3.getText().toString().equals("green")) {
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
                                    light_four_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);

                        } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
                            light_four_count[0]++;

                        } else {
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
                                    light_four_count[0]++;
                                }
                            });
                            SystemClock.sleep(seconds);
                        }
                    }
                } else {

                    int lane1_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString());
                    int lane2_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString());
                    int lane3_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString());
                    int lane4_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString());
                    PyObject roundabout = pyObj.callAttr("roundabout", lane1_val, lane2_val, lane3_val, lane4_val, c.getFilesDir().toString());
                    Log.d("rounda_ai: ", roundabout.toString());
                    char[] result = roundabout.toString().replaceAll("[\\[\\]]", "").toCharArray();
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
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (new_green == 3) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_four_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
                            if (new_green != 2) {
                                if (new_green == 1) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (new_green == 3) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                                SimulationActivity.roundabout_ai_light_2.setText("orange");
                                                SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                                SimulationActivity.roundabout_ai_light_3.setText("orange");
                                            }
                                        });
                                        SystemClock.sleep(orangetime);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                                SimulationActivity.roundabout_ai_light_2.setText("red");
                                                SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                                SimulationActivity.roundabout_ai_light_3.setText("green");
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_four_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.roundabout_ai_light_3.getText().toString().equals("green")) {
                            if (new_green != 3) {
                                if (new_green == 2) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (new_green == 1) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_four_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
                            if (new_green != 4) {
                                if (new_green == 2) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (new_green == 1) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else {
                            if (new_green == 1) {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_one_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else if (new_green == 2) {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_two_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else if (new_green == 3) {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_three_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                    return;
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
                                            light_four_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            }
                        }
                    } else {
                        Random rand = new Random();
                        int rand_no = rand.nextInt(3) + 1;
                        if (SimulationActivity.roundabout_ai_light_1.getText().toString().equals("green")) {
                            if (rand_no != 1) {
                                if (rand_no == 2) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (rand_no == 3) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_four_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.roundabout_ai_light_2.getText().toString().equals("green")) {
                            if (rand_no != 2) {
                                if (rand_no == 1) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (rand_no == 3) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                                                SimulationActivity.roundabout_ai_light_2.setText("orange");
                                                SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                                                SimulationActivity.roundabout_ai_light_3.setText("orange");
                                            }
                                        });
                                        SystemClock.sleep(orangetime);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                SimulationActivity.roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
                                                SimulationActivity.roundabout_ai_light_2.setText("red");
                                                SimulationActivity.roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_green);
                                                SimulationActivity.roundabout_ai_light_3.setText("green");
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_four_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.roundabout_ai_light_3.getText().toString().equals("green")) {
                            if (rand_no != 3) {
                                if (rand_no == 2) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (rand_no == 1) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_four_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else if (SimulationActivity.roundabout_ai_light_4.getText().toString().equals("green")) {
                            if (rand_no != 4) {
                                if (rand_no == 2) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_two_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else if (rand_no == 1) {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_one_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                } else {
                                    if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                        return;
                                    else {
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
                                                light_three_count[0]++;
                                            }
                                        });
                                        SystemClock.sleep(seconds);
                                    }
                                }
                            }
                        } else {
                            if (rand_no == 1) {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_1_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_one_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else if (rand_no == 2) {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_two_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else if (rand_no == 3) {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_3_in.getText().toString()) == 0)
                                    return;
                                else {
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
                                            light_three_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            } else {
                                if (Integer.parseInt(SimulationActivity.roundabout_ai_lane_4_in.getText().toString()) == 0)
                                    return;
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
                                            light_four_count[0]++;
                                        }
                                    });
                                    SystemClock.sleep(seconds);
                                }
                            }
                        }
                    }
                }
                // SystemClock.sleep(seconds);
            }
        }
    }
}

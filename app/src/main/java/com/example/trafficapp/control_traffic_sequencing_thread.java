package com.example.trafficapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.TextView;

public class control_traffic_sequencing_thread extends Thread {
    private Context c;
    public Looper looper;
    private TextView light_1, light_2, light_3, light_4;
    private int greentime,orangetime;
    private String junctionType;
    public Handler handler;

    control_traffic_sequencing_thread(int greentime,int orangetime, String junctionType, Context c, TextView light_1, TextView light_2, TextView light_3) {
        this.orangetime = orangetime;
        this.greentime = greentime;
        this.junctionType = junctionType;
        this.c = c;
        this.light_1 = light_1;
        this.light_2 = light_2;
        this.light_3 = light_3;
    }

    control_traffic_sequencing_thread(int greentime,int orangetime, String junctionType, Context c, TextView light_1, TextView light_2, TextView light_3, TextView light_4) {
        this.orangetime = orangetime;
        this.greentime = greentime;
        this.junctionType = junctionType;
        this.c = c;
        this.light_1 = light_1;
        this.light_2 = light_2;
        this.light_3 = light_3;
        this.light_4 = light_4;
    }

    @Override
    public void run() {
        looper = Looper.myLooper();
        handler = new Handler(Looper.getMainLooper());
        orangetime = orangetime * 1000;
        greentime = greentime * 1000;
        if (junctionType.equals("T junction")) {


            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_1.setText("orange");
                }
            });

            SystemClock.sleep(orangetime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_green);
                    light_1.setText("green");
                }
            });
            SystemClock.sleep(greentime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_1.setText("orange");
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_2.setText("orange");
                }
            });
            SystemClock.sleep(orangetime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                    light_1.setText("red");
                    light_2.setBackgroundResource(R.drawable.traffic_light_green);
                    light_2.setText("green");
                }
            });
            SystemClock.sleep(greentime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_2.setText("orange");
                    light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_3.setText("orange");
                }
            });
            SystemClock.sleep(orangetime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                    light_2.setText("red");
                    light_3.setBackgroundResource(R.drawable.traffic_light_green);
                    light_3.setText("green");
                }
            });
            SystemClock.sleep(greentime);

            while (SimulationActivity.running) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_3.setText("orange");
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_1.setText("orange");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(orangetime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_red);
                        light_3.setText("red");
                        light_1.setBackgroundResource(R.drawable.traffic_light_green);
                        light_1.setText("green");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(greentime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_1.setText("orange");
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_2.setText("orange");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(orangetime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_red);
                        light_1.setText("red");
                        light_2.setBackgroundResource(R.drawable.traffic_light_green);
                        light_2.setText("green");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(greentime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_2.setText("orange");
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_3.setText("orange");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(orangetime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_red);
                        light_2.setText("red");
                        light_3.setBackgroundResource(R.drawable.traffic_light_green);
                        light_3.setText("green");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(greentime);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                    light_1.setText("red");
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                    light_2.setText("red");
                    light_3.setBackgroundResource(R.drawable.traffic_light_red);
                    light_3.setText("red");
                }
            });
        } else {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_1.setText("orange");
                }
            });

            SystemClock.sleep(orangetime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_green);
                    light_1.setText("green");
                }
            });
            SystemClock.sleep(greentime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_1.setText("orange");
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_2.setText("orange");
                }
            });

            SystemClock.sleep(orangetime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                    light_1.setText("red");
                    light_2.setBackgroundResource(R.drawable.traffic_light_green);
                    light_2.setText("green");
                }
            });
            SystemClock.sleep(greentime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_2.setText("orange");
                    light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_3.setText("orange");
                }
            });
            SystemClock.sleep(orangetime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                    light_2.setText("red");
                    light_3.setBackgroundResource(R.drawable.traffic_light_green);
                    light_3.setText("green");
                }
            });
            SystemClock.sleep(greentime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_3.setText("orange");
                    light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                    light_4.setText("orange");
                }
            });
            SystemClock.sleep(orangetime);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_red);
                    light_3.setText("red");
                    light_4.setBackgroundResource(R.drawable.traffic_light_green);
                    light_4.setText("green");
                }
            });
            SystemClock.sleep(greentime);
            while (SimulationActivity.running) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_4.setText("orange");
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_1.setText("orange");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(orangetime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_4.setBackgroundResource(R.drawable.traffic_light_red);
                        light_4.setText("red");
                        light_1.setBackgroundResource(R.drawable.traffic_light_green);
                        light_1.setText("green");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(greentime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_1.setText("orange");
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_2.setText("orange");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(orangetime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_red);
                        light_1.setText("red");
                        light_2.setBackgroundResource(R.drawable.traffic_light_green);
                        light_2.setText("green");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(greentime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_2.setText("orange");
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_3.setText("orange");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(orangetime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_red);
                        light_2.setText("red");
                        light_3.setBackgroundResource(R.drawable.traffic_light_green);
                        light_3.setText("green");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(greentime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_3.setText("orange");
                        light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                        light_4.setText("orange");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(orangetime);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_red);
                        light_3.setText("red");
                        light_4.setBackgroundResource(R.drawable.traffic_light_green);
                        light_4.setText("green");
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(greentime);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                    light_1.setText("red");
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                    light_2.setText("red");
                    light_3.setBackgroundResource(R.drawable.traffic_light_red);
                    light_3.setText("red");
                    light_4.setBackgroundResource(R.drawable.traffic_light_red);
                    light_4.setText("red");
                }
            });
        }

    }
}

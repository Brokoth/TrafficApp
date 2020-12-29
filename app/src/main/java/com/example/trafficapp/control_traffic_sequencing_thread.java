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
    private int seconds;
    private String junctionType;
    public Handler handler;

    control_traffic_sequencing_thread(int seconds, String junctionType, Context c, TextView light_1, TextView light_2, TextView light_3) {
        this.seconds = seconds;
        this.junctionType = junctionType;
        this.c = c;
        this.light_1 = light_1;
        this.light_2 = light_2;
        this.light_3 = light_3;
    }

    control_traffic_sequencing_thread(int seconds, String junctionType, Context c, TextView light_1, TextView light_2, TextView light_3, TextView light_4) {
        this.seconds = seconds;
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
        seconds = seconds * 1000;
        if (junctionType.equals("T junction")) {


            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });

            SystemClock.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_green);
                }
            });
            SystemClock.sleep(seconds);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });

            SystemClock.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_green);
                }
            });
            SystemClock.sleep(seconds);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            SystemClock.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_green);
                }
            });
            SystemClock.sleep(seconds);

            while (SimulationActivity.running) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_red);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_green);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(seconds);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_red);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_green);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(seconds);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_red);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_green);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(seconds);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
        } else {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });

            SystemClock.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_green);
                }
            });
            SystemClock.sleep(seconds);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });

            SystemClock.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_green);
                }
            });
            SystemClock.sleep(seconds);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            SystemClock.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_green);
                }
            });
            SystemClock.sleep(seconds);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                }
            });
            SystemClock.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_4.setBackgroundResource(R.drawable.traffic_light_green);
                }
            });
            SystemClock.sleep(seconds);
            while (SimulationActivity.running) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_4.setBackgroundResource(R.drawable.traffic_light_red);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_green);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(seconds);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_1.setBackgroundResource(R.drawable.traffic_light_red);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_green);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(seconds);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_2.setBackgroundResource(R.drawable.traffic_light_red);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_green);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(seconds);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_4.setBackgroundResource(R.drawable.traffic_light_orange);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_3.setBackgroundResource(R.drawable.traffic_light_red);
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        light_4.setBackgroundResource(R.drawable.traffic_light_green);
                    }
                });
                if (!SimulationActivity.running)
                    break;
                SystemClock.sleep(seconds);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_1.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_2.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_3.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
            handler.post(new Runnable() {
                @Override
                public void run() {
                    light_4.setBackgroundResource(R.drawable.traffic_light_red);
                }
            });
        }

    }
}

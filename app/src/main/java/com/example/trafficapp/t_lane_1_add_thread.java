//package com.example.trafficapp;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.os.SystemClock;
//import android.widget.TextView;
//
//public class lanes_density_updates_thread extends Thread {
//    int seconds;
//    public Handler handler;
//    private String densityType;
//    private String junctionType;
//    private TextView con_lane_1, con_lane_2, con_lane_3, con_lane_4, ai_lane_1, ai_lane_2, ai_lane_3, ai_lane_4;
//
//    lanes_density_updates_thread(int seconds, String densityType, String junctionType, TextView con_lane_1, TextView con_lane_2, TextView con_lane_3, TextView ai_lane_1, TextView ai_lane_2, TextView ai_lane_3) {
//        this.seconds = seconds;
//        this.densityType = densityType;
//        this.junctionType = junctionType;
//        this.con_lane_1 = con_lane_1;
//        this.con_lane_2 = con_lane_2;
//        this.con_lane_3 = con_lane_3;
//        this.ai_lane_1 = ai_lane_1;
//        this.ai_lane_2 = ai_lane_2;
//        this.ai_lane_3 = ai_lane_3;
//    }
//
//    lanes_density_updates_thread(int seconds, String densityType, String junctionType, TextView con_lane_1, TextView con_lane_2, TextView con_lane_3, TextView con_lane_4, TextView ai_lane_1, TextView ai_lane_2, TextView ai_lane_3, TextView ai_lane_4) {
//        this.seconds = seconds;
//        this.densityType = densityType;
//        this.junctionType = junctionType;
//        this.con_lane_1 = con_lane_1;
//        this.con_lane_2 = con_lane_2;
//        this.con_lane_3 = con_lane_3;
//        this.con_lane_4 = con_lane_4;
//        this.ai_lane_1 = ai_lane_1;
//        this.ai_lane_2 = ai_lane_2;
//        this.ai_lane_3 = ai_lane_3;
//        this.ai_lane_4 = ai_lane_4;
//    }
//
//    @Override
//    public void run() {
//        handler = new Handler(Looper.getMainLooper());
//        seconds = seconds * 1000;
//        if (junctionType.equals("T junction")) {
//            if (densityType.equals("Heavy Traffic")) {
//                while (SimulationActivity.running) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            for (int i = 0; i < 10; i++) {
//                                int con_lane_1_val = Integer.parseInt(con_lane_1.getText().toString());
//                                int con_lane_2_val = Integer.parseInt(con_lane_1.getText().toString());
//                                int con_lane_3_val = Integer.parseInt(con_lane_1.getText().toString());
//                                int ai_lane_1_val = Integer.parseInt(con_lane_1.getText().toString());
//                                int ai_lane_2_val = Integer.parseInt(con_lane_1.getText().toString());
//                                int ai_lane_3_val = Integer.parseInt(con_lane_1.getText().toString());
//                                if
//                                con_lane_1.setText("10");
//                                con_lane_2.setText("10");
//                                con_lane_3.setText("10");
//                                ai_lane_1.setText("10");
//                                ai_lane_2.setText("10");
//                                ai_lane_3.setText("10");
//                            }
//
//                        }
//                    });
//                    SystemClock.sleep(seconds);
//                }
//            } else if (densityType.equals("Light Traffic")) {
//                while (SimulationActivity.running) {
//
//                }
//            } else {
//                while (SimulationActivity.running) {
//
//                }
//            }
//        } else {
//            if (densityType.equals("Heavy Traffic")) {
//                while (SimulationActivity.running) {
//
//                }
//            } else if (densityType.equals("Light Traffic")) {
//                while (SimulationActivity.running) {
//
//                }
//            } else {
//                while (SimulationActivity.running) {
//
//                }
//            }
//        }
//
//    }
//}
package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;

public class t_lane_1_add_thread extends Thread {
    public Handler handler;
    public int[] result;
    private String densityType, seconds;
    private String TAG = "t_lane_1_add_thread";
    private TextView con_lane_1, ai_lane_1;

    t_lane_1_add_thread(String seconds, String densityType, int[] result, TextView con_lane_1, TextView ai_lane_1) {
        this.seconds = seconds;
        this.densityType = densityType;
        this.result = result;
        this.con_lane_1 = con_lane_1;
        this.ai_lane_1 = ai_lane_1;
    }

    @Override
    public void run() {
        if (SimulationActivity.running) {
            handler = new Handler(Looper.getMainLooper());
            int add_time = Integer.parseInt(seconds);
            add_time = add_time * 1000;
            if (densityType.equals("Heavy Traffic")) {
                while (SimulationActivity.running) {
                    SystemClock.sleep(add_time);
                    for (int i = 0; i < 10; i++) {
                        if (SimulationActivity.running) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int con_lane_val = Integer.parseInt(con_lane_1.getText().toString());
                                    int ai_lane_val = Integer.parseInt(ai_lane_1.getText().toString());
                                    if (con_lane_val < 10) {
                                        con_lane_val = con_lane_val + 1;
                                        con_lane_1.setText(String.valueOf(con_lane_val));
                                    }
                                    if (ai_lane_val < 10) {
                                        ai_lane_val = ai_lane_val + 1;
                                        ai_lane_1.setText(String.valueOf(ai_lane_val));
                                    }
                                }
                            });
                            SystemClock.sleep(1000);
                        } else
                            break;

                    }
                }
            } else if (densityType.equals("Light Traffic")) {
                while (SimulationActivity.running) {
                    SystemClock.sleep(add_time);
                    for (int i = 0; i < 2; i++) {
                        if (SimulationActivity.running) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int con_lane_val = Integer.parseInt(con_lane_1.getText().toString());
                                    int ai_lane_val = Integer.parseInt(ai_lane_1.getText().toString());
                                    if (con_lane_val < 10) {
                                        con_lane_val = con_lane_val + 1;
                                        con_lane_1.setText(String.valueOf(con_lane_val));
                                    }
                                    if (ai_lane_val < 10) {
                                        ai_lane_val = ai_lane_val + 1;
                                        ai_lane_1.setText(String.valueOf(ai_lane_val));
                                    }

                                }
                            });
                            SystemClock.sleep(1000);
                        } else
                            break;

                    }
                }
            } else if (densityType.equals("Randomized")) {
                while (SimulationActivity.running) {
                    SystemClock.sleep(add_time);
                    Random rand = new Random();
                    int rand_no = rand.nextInt(11);
                    for (int i = 0; i < rand_no; i++) {
                        if (SimulationActivity.running) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int con_lane_val = Integer.parseInt(con_lane_1.getText().toString());
                                    int ai_lane_val = Integer.parseInt(ai_lane_1.getText().toString());
                                    if (con_lane_val < 10) {
                                        con_lane_val = con_lane_val + 1;
                                        con_lane_1.setText(String.valueOf(con_lane_val));
                                    }
                                    if (ai_lane_val < 10) {
                                        ai_lane_val = ai_lane_val + 1;
                                        ai_lane_1.setText(String.valueOf(ai_lane_val));
                                    }

                                }
                            });
                            SystemClock.sleep(1000);
                        } else
                            break;

                    }
                }
            } else {
                int length = Array.getLength(result);
                while (SimulationActivity.running) {
                    for (int i = 0; i < length; i++) {
                        if (SimulationActivity.running) {
                            Log.d(TAG, "run: " + result[i]);
                            if (result[i] > 10) {
                                result[i] = 10;
                                for (int x = 0; x < result[i]; x++) {
                                    if (SimulationActivity.running) {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                int con_lane_val = Integer.parseInt(con_lane_1.getText().toString());
                                                int ai_lane_val = Integer.parseInt(ai_lane_1.getText().toString());
                                                if (con_lane_val < 10) {
                                                    con_lane_val = con_lane_val + 1;
                                                    con_lane_1.setText(String.valueOf(con_lane_val));
                                                }
                                                if (ai_lane_val < 10) {
                                                    ai_lane_val = ai_lane_val + 1;
                                                    ai_lane_1.setText(String.valueOf(ai_lane_val));
                                                }
                                            }
                                        });
                                        SystemClock.sleep(1000);
                                    } else
                                        break;
                                }
                            } else {
                                for (int x = 0; x < result[i]; x++) {
                                    if (SimulationActivity.running) {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                int con_lane_val = Integer.parseInt(con_lane_1.getText().toString());
                                                int ai_lane_val = Integer.parseInt(ai_lane_1.getText().toString());
                                                if (con_lane_val < 10) {
                                                    con_lane_val = con_lane_val + 1;
                                                    con_lane_1.setText(String.valueOf(con_lane_val));
                                                }
                                                if (ai_lane_val < 10) {
                                                    ai_lane_val = ai_lane_val + 1;
                                                    ai_lane_1.setText(String.valueOf(ai_lane_val));
                                                }
                                            }
                                        });
                                        SystemClock.sleep(1000);
                                    } else
                                        break;
                                }
                            }
                            SystemClock.sleep(add_time);
                        } else
                            break;
                    }
                }
            }

        }
    }
}

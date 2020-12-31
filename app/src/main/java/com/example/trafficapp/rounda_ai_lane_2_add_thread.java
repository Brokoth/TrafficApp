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

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import java.util.Random;

public class rounda_ai_lane_2_add_thread extends Thread {
    int seconds;
    public Handler handler;
    private String densityType;
    private String TAG = "rounda_ai_lane_2_add_thread";

    rounda_ai_lane_2_add_thread(int seconds, String densityType) {
        this.seconds = seconds;
        this.densityType = densityType;
    }

    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        seconds = seconds * 1000;
        while (SimulationActivity.running) {
            SystemClock.sleep(seconds);
            if (densityType.equals("Heavy Traffic")) {
                for (int i = 0; i < 10; i++) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int lane_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString());
                            if (lane_val < 10) {
                                lane_val = lane_val + 1;
                                SimulationActivity.roundabout_ai_lane_2_in.setText(String.valueOf(lane_val));
                                Log.d(TAG, "run: " + lane_val);
                            }

                        }
                    });
                    SystemClock.sleep(1000);
                }
            } else if (densityType.equals("Light Traffic")) {
                for (int i = 0; i < 2; i++) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int lane_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString());
                            if (lane_val < 10) {
                                lane_val = lane_val + 1;
                                SimulationActivity.roundabout_ai_lane_2_in.setText(String.valueOf(lane_val));
                                Log.d(TAG, "run: " + lane_val);
                            }

                        }
                    });
                    SystemClock.sleep(1000);
                }
            } else {
                Random rand = new Random();
                int rand_no = rand.nextInt(11);
                for (int i = 0; i < rand_no; i++) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int lane_val = Integer.parseInt(SimulationActivity.roundabout_ai_lane_2_in.getText().toString());
                            if (lane_val < 10) {
                                lane_val = lane_val + 1;
                                SimulationActivity.roundabout_ai_lane_2_in.setText(String.valueOf(lane_val));
                                Log.d(TAG, "run: " + lane_val++);
                            }

                        }
                    });
                    SystemClock.sleep(1000);
                }
            }

        }
    }
}

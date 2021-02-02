package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tjun_efficiency_thread extends Thread {
    private TextView ai_lane_1, ai_lane_2, ai_lane_3, con_lane_1, con_lane_2, con_lane_3;
    private Context c;
    private String simulationid, userid;
    private String TAG = "tjun_efficiency_thread";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Handler handler;
    private static float ai_lane1_val_1, ai_lane2_val_1, ai_lane3_val_1, ai_lane1_val_2, ai_lane2_val_2, ai_lane3_val_2,
            con_lane1_val_1, con_lane2_val_1, con_lane3_val_1, con_lane1_val_2, con_lane2_val_2, con_lane3_val_2;

    tjun_efficiency_thread(TextView ai_lane_1, TextView ai_lane_2, TextView ai_lane_3, TextView con_lane_1, TextView con_lane_2, TextView con_lane_3, Context c, String simulationid, String userid) {
        this.ai_lane_1 = ai_lane_1;
        this.c = c;
        this.ai_lane_2 = ai_lane_2;
        this.ai_lane_3 = ai_lane_3;
        this.con_lane_1 = con_lane_1;
        this.con_lane_2 = con_lane_2;
        this.con_lane_3 = con_lane_3;
        this.simulationid = simulationid;
        this.userid = userid;

    }


    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        float seconds = 5000;
        String all_ai_coords = "";
        String all_con_coords = "";
        float sum_of_average_ai_change = 0;
        float sum_of_average_con_change = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        float count = 0;
        while (SimulationActivity.running) {
            count = count + 1;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ai_lane1_val_1 = Integer.parseInt(ai_lane_1.getText().toString());
                    ai_lane2_val_1 = Integer.parseInt(ai_lane_2.getText().toString());
                    ai_lane3_val_1 = Integer.parseInt(ai_lane_3.getText().toString());
                    con_lane1_val_1 = Integer.parseInt(con_lane_1.getText().toString());
                    con_lane2_val_1 = Integer.parseInt(con_lane_2.getText().toString());
                    con_lane3_val_1 = Integer.parseInt(con_lane_3.getText().toString());
                }
            });
            SystemClock.sleep(Math.round(seconds));
            String current_time = sdf.format(new Date());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ai_lane1_val_2 = Integer.parseInt(ai_lane_1.getText().toString());
                    ai_lane2_val_2 = Integer.parseInt(ai_lane_2.getText().toString());
                    ai_lane3_val_2 = Integer.parseInt(ai_lane_3.getText().toString());
                    con_lane1_val_2 = Integer.parseInt(con_lane_1.getText().toString());
                    con_lane2_val_2 = Integer.parseInt(con_lane_2.getText().toString());
                    con_lane3_val_2 = Integer.parseInt(con_lane_3.getText().toString());
                }
            });
            float ai_lane_1_valchange = ai_lane1_val_2 - ai_lane1_val_1;
            float ai_lane_2_valchange = ai_lane2_val_2 - ai_lane2_val_1;
            float ai_lane_3_valchange = ai_lane3_val_2 - ai_lane3_val_1;
            float con_lane_1_valchange = con_lane1_val_2 - con_lane1_val_1;
            float con_lane_2_valchange = con_lane2_val_2 - con_lane2_val_1;
            float con_lane_3_valchange = con_lane3_val_2 - con_lane3_val_1;
            float average_ai_change = (((ai_lane_1_valchange / ai_lane1_val_1) * 100) + ((ai_lane_2_valchange / ai_lane2_val_1) * 100) + ((ai_lane_3_valchange / ai_lane3_val_1) * 100)) / 3;
            // float average_ai_change_rate = average_ai_change / seconds;
            all_ai_coords = all_ai_coords + "(" + average_ai_change + "," + current_time + ")-";
            sum_of_average_ai_change = sum_of_average_ai_change + average_ai_change;
            float average_con_change = (((con_lane_1_valchange / con_lane1_val_1) * 100) + ((con_lane_2_valchange / con_lane2_val_1) * 100) + ((con_lane_3_valchange / con_lane3_val_1) * 100)) / 3;
           // float average_con_change_rate = average_con_change / seconds;
            all_con_coords = all_con_coords + "(" + average_con_change + "," + current_time + ")-";
            sum_of_average_con_change = sum_of_average_con_change + average_con_change;
            // Log.d(TAG, "average ai change= " + average_ai_change + " average con change= " + average_con_change);
        }
        DecimalFormat df = new DecimalFormat("######.#######");
        float average_of_average_ai_change = sum_of_average_ai_change / count;
        float average_of_average_con_change = sum_of_average_con_change/ count;
        Log.d(TAG, "average_of_average_ai_change= " + df.format(average_of_average_ai_change) + " average_of_average_con_change= " + df.format(average_of_average_con_change) + " count= " + count + " sum_of_average_ai_change= " +
                sum_of_average_ai_change + " sum_of_average_con_change= " + sum_of_average_con_change);
        String end_time = sdf.format(new Date());
        String finalAll_con_coords = all_con_coords;
        String finalAll_ai_coords = all_ai_coords;
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(c, "Terminating simulation", Toast.LENGTH_SHORT).show();
                db.collection("Users").document(userid).collection("Simulations").document(simulationid).
                        update("ai_density_change_rate_coords", finalAll_ai_coords, "ai_efficiency", String.valueOf(df.format(average_of_average_ai_change)),
                                "con_density_change_rate_coords", finalAll_con_coords, "control_efficiency", String.valueOf(df.format(average_of_average_con_change)), "end_time", end_time).
                        addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(c, "Terminated simulation Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(c, "Failed to upload simulation end time", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}

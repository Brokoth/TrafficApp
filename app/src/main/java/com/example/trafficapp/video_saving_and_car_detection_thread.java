package com.example.trafficapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class video_saving_and_car_detection_thread extends Thread {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private String frame_interval, link1, link2, link3, link4, path;
    private Button gbtn;
    private ProgressBar pbar;
    private Context c;
    public Handler handler;

    video_saving_and_car_detection_thread(Button gbtn, ProgressBar pbar, String frame_interval, String link1, String link2, String link3, String link4, Context c, String path) {
        this.frame_interval = frame_interval;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.link4 = link4;
        this.c = c;
        this.path = path;
        this.gbtn = gbtn;
        this.pbar = pbar;
    }


    @Override
    public void run() {
        handler = new Handler(Looper.getMainLooper());
        int count = 0;
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        while (count == 0) {
            if (!Python.isStarted())
                Python.start(new AndroidPlatform(c));
            Python python = Python.getInstance();
            PyObject pyObj = python.getModule("video_to_density_converter_script");
            PyObject traffic_values = pyObj.callAttr("vid_saving", link1, link2, link3, link4, frame_interval, path);
            String[] arrSplit = traffic_values.toString().split("/");
            String link1_vals = arrSplit[0];
            String link2_vals = arrSplit[1];
            String link3_vals = arrSplit[2];
            String link4_vals = arrSplit[3];
           // Log.e("traffic values", "run: " +link1_vals+link2_vals+link3_vals+link4_vals);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    db.collection("Users").document(uid).collection("Simulation_Settings")
                            .document("link_values").update("link_one_values", link1_vals, "link_two_values", link2_vals, "link_three_values", link3_vals, "link_four_values", link4_vals)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pbar.setVisibility(View.GONE);
                                    gbtn.setVisibility(View.VISIBLE);
                                    Toast.makeText(c, "Done.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pbar.setVisibility(View.GONE);
                            gbtn.setVisibility(View.VISIBLE);
                            Toast.makeText(c, "Failed to generate values. Try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            count = count + 1;
        }
    }
}


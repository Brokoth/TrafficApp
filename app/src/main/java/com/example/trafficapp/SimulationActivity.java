package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.Random;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SimulationActivity extends AppCompatActivity {
    private Handler mainHandler;
    private Looper controlLooper;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private ImageView back;
    private Button startsim, stopsim;
    public static Boolean running = false;
    static Spinner junctionspinner = null, densityspinner = null;
    private LinearLayout tjunction_layout, roundabout_layout, j_selection_layout, d_selection_layout;
    public static TextView tjunction_con_light_1, tjunction_con_light_2, tjunction_con_light_3, tjunction_ai_light_1, tjunction_ai_light_2, tjunction_ai_light_3,
            roundabout_con_light_1, roundabout_con_light_2, roundabout_con_light_3, roundabout_con_light_4, roundabout_ai_light_1, roundabout_ai_light_2,
            roundabout_ai_light_3, roundabout_ai_light_4;
    public static TextView runningLabel, tjunction_con_lane_1_in, tjunction_con_lane_3_in,
            tjunction_ai_lane_1_in, tjunction_ai_lane_3_in, roundabout_con_lane_1_in, roundabout_con_lane_3_in,
            roundabout_ai_lane_1_in, roundabout_ai_lane_3_in, roundabout_ai_lane_2_in, roundabout_ai_lane_4_in,
            roundabout_con_lane_2_in, roundabout_con_lane_4_in, tjunction_con_lane_2_in, tjunction_ai_lane_2_in;
    private static control_traffic_sequencing_thread controlTrafficSequencingThread;
    private static ai_traffic_sequencing_thread aiTrafficSequencingThread;
    private static t_con_lane_1_add_thread conLane1AddThread;
    private static t_con_lane_1_sub_thread conLane1SubThread;
    private static t_con_lane_2_add_thread conLane2AddThread;
    private static t_con_lane_2_sub_thread conLane2SubThread;
    private static t_con_lane_3_add_thread conLane3AddThread;
    private static t_con_lane_3_sub_thread conLane3SubThread;
    private static t_ai_lane_1_add_thread aiLane1AddThread;
    private static t_ai_lane_1_sub_thread aiLane1SubThread;
    private static t_ai_lane_2_add_thread aiLane2AddThread;
    private static t_ai_lane_2_sub_thread aiLane2SubThread;
    private static t_ai_lane_3_add_thread aiLane3AddThread;
    private static t_ai_lane_3_sub_thread aiLane3SubThread;
    private static rounda_con_lane_1_add_thread roundaConLane1AddThread;
    private static rounda_con_lane_1_sub_thread roundaConLane1SubThread;
    private static rounda_con_lane_2_add_thread roundaConLane2AddThread;
    private static rounda_con_lane_2_sub_thread roundaConLane2SubThread;
    private static rounda_con_lane_3_add_thread roundaConLane3AddThread;
    private static rounda_con_lane_3_sub_thread roundaConLane3SubThread;
    private static rounda_con_lane_4_add_thread roundaConLane4AddThread;
    private static rounda_con_lane_4_sub_thread roundaConLane4SubThread;
    private static rounda_ai_lane_1_add_thread roundaAiLane1AddThread;
    private static rounda_ai_lane_1_sub_thread roundaAiLane1SubThread;
    private static rounda_ai_lane_2_add_thread roundaAiLane2AddThread;
    private static rounda_ai_lane_2_sub_thread roundaAiLane2SubThread;
    private static rounda_ai_lane_3_add_thread roundaAiLane3AddThread;
    private static rounda_ai_lane_3_sub_thread roundaAiLane3SubThread;
    private static rounda_ai_lane_4_add_thread roundaAiLane4AddThread;
    private static rounda_ai_lane_4_sub_thread roundaAiLane4SubThread;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_sim);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        db = FirebaseFirestore.getInstance();
        runningLabel = findViewById(R.id.running_label);
        j_selection_layout = findViewById(R.id.junction_selection_layout);
        d_selection_layout = findViewById(R.id.density_selection_layout);
        tjunction_layout = findViewById(R.id.tjunction);
        roundabout_layout = findViewById(R.id.roundabout);
        tjunction_con_light_1 = findViewById(R.id.t_con_light_1);
        tjunction_con_light_2 = findViewById(R.id.t_con_light_2);
        tjunction_con_light_3 = findViewById(R.id.t_con_light_3);
        tjunction_ai_light_1 = findViewById(R.id.t_ai_light_1);
        tjunction_ai_light_2 = findViewById(R.id.t_ai_light_2);
        tjunction_ai_light_3 = findViewById(R.id.t_ai_light_3);
        tjunction_con_lane_1_in = findViewById(R.id.t_con_lane_1_in);
        tjunction_con_lane_2_in = findViewById(R.id.t_con_lane_2_in);
        tjunction_con_lane_3_in = findViewById(R.id.t_con_lane_3_in);
        tjunction_ai_lane_1_in = findViewById(R.id.t_ai_lane_1_in);
        tjunction_ai_lane_2_in = findViewById(R.id.t_ai_lane_2_in);
        tjunction_ai_lane_3_in = findViewById(R.id.t_ai_lane_3_in);
        roundabout_con_light_1 = findViewById(R.id.rounda_con_light_1);
        roundabout_con_light_2 = findViewById(R.id.rounda_con_light_2);
        roundabout_con_light_3 = findViewById(R.id.rounda_con_light_3);
        roundabout_con_light_4 = findViewById(R.id.rounda_con_light_4);
        roundabout_ai_light_1 = findViewById(R.id.rounda_ai_light_1);
        roundabout_ai_light_2 = findViewById(R.id.rounda_ai_light_2);
        roundabout_ai_light_3 = findViewById(R.id.rounda_ai_light_3);
        roundabout_ai_light_4 = findViewById(R.id.rounda_ai_light_4);
        roundabout_con_lane_1_in = findViewById(R.id.rounda_con_lane_1_in);
        roundabout_con_lane_2_in = findViewById(R.id.rounda_con_lane_2_in);
        roundabout_con_lane_3_in = findViewById(R.id.rounda_con_lane_3_in);
        roundabout_con_lane_4_in = findViewById(R.id.rounda_con_lane_4_in);
        roundabout_ai_lane_1_in = findViewById(R.id.rounda_ai_lane_1_in);
        roundabout_ai_lane_2_in = findViewById(R.id.rounda_ai_lane_2_in);
        roundabout_ai_lane_3_in = findViewById(R.id.rounda_ai_lane_3_in);
        roundabout_ai_lane_4_in = findViewById(R.id.rounda_ai_lane_4_in);
        startsim = findViewById(R.id.run_btn);
        stopsim = findViewById(R.id.stop_btn);
        back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Simulation");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop_simulation();
                Intent intent = new Intent(SimulationActivity.this,
                        HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        junctionspinner = findViewById(R.id.junction_type_spin);
        ArrayAdapter<CharSequence> junctionSpinneradapter = ArrayAdapter.createFromResource(this, R.array.junctionTypes, R.layout.spinner_item);
        junctionSpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        junctionspinner.setAdapter(junctionSpinneradapter);
        densityspinner = findViewById(R.id.density_type_spin);
        ArrayAdapter<CharSequence> densitySpinneradapter = ArrayAdapter.createFromResource(this, R.array.densityTypes, R.layout.spinner_item);
        densitySpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        densityspinner.setAdapter(densitySpinneradapter);
        junctionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if ("T junction".equals(junctionspinner.getSelectedItem().toString())) {
                    tjunction_layout.setVisibility(View.VISIBLE);
                    roundabout_layout.setVisibility(View.GONE);

                } else {
                    tjunction_layout.setVisibility(View.GONE);
                    roundabout_layout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        startsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
                startsim.setVisibility(View.GONE);
                stopsim.setVisibility(View.VISIBLE);
                j_selection_layout.setVisibility(View.GONE);
                d_selection_layout.setVisibility(View.GONE);
                runningLabel.setVisibility(View.VISIBLE);
                final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                POJO_simulation simulation = new POJO_simulation(id, junctionspinner.getSelectedItem().toString(), densityspinner.getSelectedItem().toString());
                db.collection("Simulations").add(simulation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        String simulationId = documentReference.getId();
                        db.collection("Simulations").document(simulationId).update("simulationId", simulationId).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                if (junctionspinner.getSelectedItem().toString().equals("T junction")) {
                                    conLane1AddThread = new t_con_lane_1_add_thread(5, densityspinner.getSelectedItem().toString());
                                    conLane1AddThread.start();
                                    conLane1SubThread = new t_con_lane_1_sub_thread(1);
                                    conLane1SubThread.start();
                                    conLane2AddThread = new t_con_lane_2_add_thread(5, densityspinner.getSelectedItem().toString());
                                    conLane2AddThread.start();
                                    conLane2SubThread = new t_con_lane_2_sub_thread(1);
                                    conLane2SubThread.start();
                                    conLane3AddThread = new t_con_lane_3_add_thread(5, densityspinner.getSelectedItem().toString());
                                    conLane3AddThread.start();
                                    conLane3SubThread = new t_con_lane_3_sub_thread(1);
                                    conLane3SubThread.start();
                                    aiLane1AddThread = new t_ai_lane_1_add_thread(5, densityspinner.getSelectedItem().toString());
                                    aiLane1AddThread.start();
                                    aiLane1SubThread = new t_ai_lane_1_sub_thread(1);
                                    aiLane1SubThread.start();
                                    aiLane2AddThread = new t_ai_lane_2_add_thread(5, densityspinner.getSelectedItem().toString());
                                    aiLane2AddThread.start();
                                    aiLane2SubThread = new t_ai_lane_2_sub_thread(1);
                                    aiLane2SubThread.start();
                                    aiLane3AddThread = new t_ai_lane_3_add_thread(5, densityspinner.getSelectedItem().toString());
                                    aiLane3AddThread.start();
                                    aiLane3SubThread = new t_ai_lane_3_sub_thread(1);
                                    aiLane3SubThread.start();
                                    controlTrafficSequencingThread = new control_traffic_sequencing_thread(10, junctionspinner.getSelectedItem().toString(), SimulationActivity.this, tjunction_con_light_1, tjunction_con_light_2, tjunction_con_light_3);
                                    controlTrafficSequencingThread.start();
                                    //  aiTrafficSequencingThread = new ai_traffic_sequencing_thread(junctionspinner.getSelectedItem().toString(),SimulationActivity.this,tjunction_ai_light_1,tjunction_ai_light_2,tjunction_ai_light_3);
                                    // aiTrafficSequencingThread.start();
                                    if (densityspinner.getSelectedItem().toString().equals("Heavy Traffic")) {
                                        tjunction_con_lane_1_in.setText("10");
                                        tjunction_con_lane_2_in.setText("10");
                                        tjunction_con_lane_3_in.setText("10");
                                        tjunction_ai_lane_1_in.setText("10");
                                        tjunction_ai_lane_2_in.setText("10");
                                        tjunction_ai_lane_3_in.setText("10");

                                    } else if (densityspinner.getSelectedItem().toString().equals("Light Traffic")) {
                                        tjunction_con_lane_1_in.setText("2");
                                        tjunction_con_lane_2_in.setText("2");
                                        tjunction_con_lane_3_in.setText("2");
                                        tjunction_ai_lane_1_in.setText("2");
                                        tjunction_ai_lane_2_in.setText("2");
                                        tjunction_ai_lane_3_in.setText("2");
                                    } else {
                                        Random rand = new Random();
                                        int rand_no = rand.nextInt(11);
                                        tjunction_con_lane_1_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        tjunction_con_lane_2_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        tjunction_con_lane_3_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        tjunction_ai_lane_1_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        tjunction_ai_lane_2_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        tjunction_ai_lane_3_in.setText(String.valueOf(rand_no));
                                    }
                                } else {
                                    roundaConLane1AddThread = new rounda_con_lane_1_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaConLane1AddThread.start();
                                    roundaConLane1SubThread = new rounda_con_lane_1_sub_thread(1);
                                    roundaConLane1SubThread.start();
                                    roundaConLane2AddThread = new rounda_con_lane_2_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaConLane2AddThread.start();
                                    roundaConLane2SubThread = new rounda_con_lane_2_sub_thread(1);
                                    roundaConLane2SubThread.start();
                                    roundaConLane3AddThread = new rounda_con_lane_3_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaConLane3AddThread.start();
                                    roundaConLane3SubThread = new rounda_con_lane_3_sub_thread(1);
                                    roundaConLane3SubThread.start();
                                    roundaConLane4AddThread = new rounda_con_lane_4_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaConLane4AddThread.start();
                                    roundaConLane4SubThread = new rounda_con_lane_4_sub_thread(1);
                                    roundaConLane4SubThread.start();
                                    roundaAiLane1AddThread = new rounda_ai_lane_1_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaAiLane1AddThread.start();
                                    roundaAiLane1SubThread = new rounda_ai_lane_1_sub_thread(1);
                                    roundaAiLane1SubThread.start();
                                    roundaAiLane2AddThread = new rounda_ai_lane_2_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaAiLane2AddThread.start();
                                    roundaAiLane2SubThread = new rounda_ai_lane_2_sub_thread(1);
                                    roundaAiLane2SubThread.start();
                                    roundaAiLane3AddThread = new rounda_ai_lane_3_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaAiLane3AddThread.start();
                                    roundaAiLane3SubThread = new rounda_ai_lane_3_sub_thread(1);
                                    roundaAiLane3SubThread.start();
                                    roundaAiLane4AddThread = new rounda_ai_lane_4_add_thread(5, densityspinner.getSelectedItem().toString());
                                    roundaAiLane4AddThread.start();
                                    roundaAiLane4SubThread = new rounda_ai_lane_4_sub_thread(1);
                                    roundaAiLane4SubThread.start();
                                    controlTrafficSequencingThread = new control_traffic_sequencing_thread(10, junctionspinner.getSelectedItem().toString(), SimulationActivity.this, roundabout_con_light_1, roundabout_con_light_2, roundabout_con_light_3, roundabout_con_light_4);
                                    controlTrafficSequencingThread.start();
                                    // aiTrafficSequencingThread = new ai_traffic_sequencing_thread(junctionspinner.getSelectedItem().toString(),SimulationActivity.this,roundabout_ai_light_1,roundabout_ai_light_2,roundabout_ai_light_3,roundabout_ai_light_4);
                                    // aiTrafficSequencingThread.start();
                                    if (densityspinner.getSelectedItem().toString().equals("Heavy Traffic")) {
                                        roundabout_con_lane_1_in.setText("10");
                                        roundabout_con_lane_2_in.setText("10");
                                        roundabout_con_lane_3_in.setText("10");
                                        roundabout_con_lane_4_in.setText("10");
                                        roundabout_ai_lane_1_in.setText("10");
                                        roundabout_ai_lane_2_in.setText("10");
                                        roundabout_ai_lane_3_in.setText("10");
                                        roundabout_ai_lane_4_in.setText("10");
                                    } else if (densityspinner.getSelectedItem().toString().equals("Light Traffic")) {
                                        roundabout_con_lane_1_in.setText("2");
                                        roundabout_con_lane_2_in.setText("2");
                                        roundabout_con_lane_3_in.setText("2");
                                        roundabout_con_lane_4_in.setText("2");
                                        roundabout_ai_lane_1_in.setText("2");
                                        roundabout_ai_lane_2_in.setText("2");
                                        roundabout_ai_lane_3_in.setText("2");
                                        roundabout_ai_lane_4_in.setText("2");
                                    } else {
                                        Random rand = new Random();
                                        int rand_no = rand.nextInt(11);
                                        roundabout_con_lane_1_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        roundabout_con_lane_2_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        roundabout_con_lane_3_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        roundabout_con_lane_4_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        roundabout_ai_lane_1_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        roundabout_ai_lane_2_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        roundabout_ai_lane_3_in.setText(String.valueOf(rand_no));
                                        rand_no = rand.nextInt(11);
                                        roundabout_ai_lane_4_in.setText(String.valueOf(rand_no));
                                    }
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });
        stopsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop_simulation();
            }
        });

        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationActivity.this, LoginActivity.class));
        }

    }


    private void stop_simulation() {
        running = false;
        // controlTrafficSequencingThread.looper.quitSafely();
//        tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
//        tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
//        tjunction_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
//        tjunction_con_light_1.setBackgroundResource(R.drawable.traffic_light_red);
//        tjunction_con_light_2.setBackgroundResource(R.drawable.traffic_light_red);
//        tjunction_con_light_3.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_ai_light_1.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_ai_light_2.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_ai_light_3.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_ai_light_4.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_con_light_1.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_con_light_2.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_con_light_3.setBackgroundResource(R.drawable.traffic_light_red);
//        roundabout_con_light_4.setBackgroundResource(R.drawable.traffic_light_red);
        startsim.setVisibility(View.VISIBLE);
        stopsim.setVisibility(View.GONE);
        j_selection_layout.setVisibility(View.VISIBLE);
        d_selection_layout.setVisibility(View.VISIBLE);
        runningLabel.setVisibility(View.GONE);
        //controlTrafficSequencingThread.
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        stop_simulation();
        Intent intent = new Intent(SimulationActivity.this,
                HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationActivity.this, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                stop_simulation();

                auth.signOut();
                startActivity(new Intent(SimulationActivity.this, LoginActivity.class));
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SimulationActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static String simulationid, id;
    private FirebaseFirestore db;
    private ImageView back, sim_settings;
    private Button startsim, stopsim;
    private ProgressBar tjunpbar, roundapbar;
    public static Boolean running = false;
    private Spinner junctionspinner = null, densityspinner = null;
    private LinearLayout tjunction_layout, roundabout_layout, j_selection_layout, d_selection_layout;
    public static TextView tjunction_con_light_1, tjunction_con_light_2, tjunction_con_light_3, tjunction_ai_light_1, tjunction_ai_light_2, tjunction_ai_light_3,
            roundabout_con_light_1, roundabout_con_light_2, roundabout_con_light_3, roundabout_con_light_4, roundabout_ai_light_1, roundabout_ai_light_2,
            roundabout_ai_light_3, roundabout_ai_light_4;
    public static TextView runningLabel, tjunction_con_lane_1_in, tjunction_con_lane_3_in,
            tjunction_ai_lane_1_in, tjunction_ai_lane_3_in, roundabout_con_lane_1_in, roundabout_con_lane_3_in,
            roundabout_ai_lane_1_in, roundabout_ai_lane_3_in, roundabout_ai_lane_2_in, roundabout_ai_lane_4_in,
            roundabout_con_lane_2_in, roundabout_con_lane_4_in, tjunction_con_lane_2_in, tjunction_ai_lane_2_in;
    private control_traffic_sequencing_thread controlTrafficSequencingThread;
    private rounda_efficiency_thread roundaEfficiencyThread;
    private tjun_efficiency_thread tjunEfficiencyThread;
    private ai_traffic_sequencing_thread aiTrafficSequencingThread;
    private t_lane_1_add_thread conLane1AddThread;
    private t_con_lane_1_sub_thread conLane1SubThread;
    private t_lane_2_add_thread conLane2AddThread;
    private t_con_lane_2_sub_thread conLane2SubThread;
    private t_lane_3_add_thread conLane3AddThread;
    private t_con_lane_3_sub_thread conLane3SubThread;
    private t_ai_lane_1_sub_thread aiLane1SubThread;
    private t_ai_lane_2_sub_thread aiLane2SubThread;
    private t_ai_lane_3_sub_thread aiLane3SubThread;
    private rounda_lane_1_add_thread roundaLane1AddThread;
    private rounda_con_lane_1_sub_thread roundaConLane1SubThread;
    private rounda_lane_2_add_thread roundaLane2AddThread;
    private rounda_con_lane_2_sub_thread roundaConLane2SubThread;
    private rounda_lane_3_add_thread roundaLane3AddThread;
    private rounda_con_lane_3_sub_thread roundaConLane3SubThread;
    private rounda_lane_4_add_thread roundaLane4AddThread;
    private rounda_con_lane_4_sub_thread roundaConLane4SubThread;
    private rounda_ai_lane_1_sub_thread roundaAiLane1SubThread;
    private rounda_ai_lane_2_sub_thread roundaAiLane2SubThread;
    private rounda_ai_lane_3_sub_thread roundaAiLane3SubThread;
    private rounda_ai_lane_4_sub_thread roundaAiLane4SubThread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_sim);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        db = FirebaseFirestore.getInstance();
        runningLabel = findViewById(R.id.running_label);
        tjunpbar = findViewById(R.id.tjunprogressBar);
        roundapbar = findViewById(R.id.roundaprogressBar);
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
        sim_settings = findViewById(R.id.simulation_settings_btn);
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

        sim_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SimulationActivity.this,
                        SimulationSettingsActivity.class);
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
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ssdd/MM/yyyy");
                String start_time = sdf.format(new Date());
                String milliseconds_start_time = sdf2.format(new Date());
                Date mDate = null;
                try {
                    mDate = sdf2.parse(milliseconds_start_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String timeInMilliseconds = String.valueOf(mDate.getTime());

                startsim.setVisibility(View.GONE);
                stopsim.setVisibility(View.VISIBLE);
                sim_settings.setVisibility(View.GONE);
                j_selection_layout.setVisibility(View.GONE);
                d_selection_layout.setVisibility(View.GONE);
                //String timestamp = FieldValue.serverTimestamp().toString();
                runningLabel.setVisibility(View.VISIBLE);
                id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                db.collection("Users").document(id).collection("Simulation_Settings")
                        .document("links").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String laneone_link = documentSnapshot.getString("link_one");
                        String lanetwo_link = documentSnapshot.getString("link_two");
                        String lanethree_link = documentSnapshot.getString("link_three");
                        String lanefour_link = documentSnapshot.getString("link_four");
                        String frame_gap = documentSnapshot.getString("frame_interval");
                        db.collection("Users").document(id).collection("Simulation_Settings")
                                .document("link_values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String laneone_vals = documentSnapshot.getString("link_one_values");
                                String lanetwo_vals = documentSnapshot.getString("link_two_values");
                                String lanethree_vals = documentSnapshot.getString("link_three_values");
                                String lanefour_vals = documentSnapshot.getString("link_four_values");
                                laneone_vals = laneone_vals.replaceAll("[\\[\\] ]", "");
                                String[] lane_one_str_values_array = laneone_vals.split(",");
                                int length1 = Array.getLength(lane_one_str_values_array);
                                int[] lane1_int_array = new int[length1];
                                for (int i = 0; i < length1; i++) {
                                    lane1_int_array[i] = Integer.parseInt(lane_one_str_values_array[i]);
                                }
                                lanetwo_vals = lanetwo_vals.replaceAll("[\\[\\] ]", "");
                                String[] lane_two_str_values_array = lanetwo_vals.split(",");
                                int length2 = Array.getLength(lane_two_str_values_array);
                                int[] lane2_int_array = new int[length2];
                                for (int i = 0; i < length2; i++) {
                                    lane2_int_array[i] = Integer.parseInt(lane_two_str_values_array[i]);
                                }
                                lanethree_vals = lanethree_vals.replaceAll("[\\[\\] ]", "");
                                String[] lane_three_str_values_array = lanethree_vals.split(",");
                                int length3 = Array.getLength(lane_three_str_values_array);
                                int[] lane3_int_array = new int[length3];
                                for (int i = 0; i < length3; i++) {
                                    lane3_int_array[i] = Integer.parseInt(lane_three_str_values_array[i]);
                                }
                                lanefour_vals = lanefour_vals.replaceAll("[\\[\\] ]", "");
                                String[] lane_four_str_values_array = lanefour_vals.split(",");
                                int length4 = Array.getLength(lane_four_str_values_array);
                                int[] lane4_int_array = new int[length4];
                                for (int i = 0; i < length4; i++) {
                                    lane4_int_array[i] = Integer.parseInt(lane_four_str_values_array[i]);
                                }
                                db.collection("Users").document(id).collection("Simulation_Settings")
                                        .document("timings").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String addition_Time = documentSnapshot.getString("additionTime");
                                        String green_Time = documentSnapshot.getString("greenTime");
                                        String orange_Time = documentSnapshot.getString("orangeTime");
                                        String subtraction_Time = documentSnapshot.getString("subtractionTime");
                                        if (densityspinner.getSelectedItem().toString().equals("Custom")) {
                                            if (laneone_link.equals("") || lanetwo_link.equals("") || lanethree_link.equals("") || lanefour_link.equals("")
                                                    || laneone_link == null || lanetwo_link == null || lanethree_link == null || lanefour_link == null) {
                                                Toast.makeText(SimulationActivity.this, "Add some custom footage in the settings first", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else {

                                                POJO_simulation simulation =
                                                        new POJO_simulation(id, junctionspinner.getSelectedItem().toString(), densityspinner.getSelectedItem().toString(),
                                                                green_Time, orange_Time, addition_Time, laneone_link, lanetwo_link,
                                                                lanethree_link, lanefour_link, frame_gap, start_time, "", timeInMilliseconds, "", "", "", "");
                                                db.collection("Users").document(id).collection("Simulations").add(simulation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        simulationid = documentReference.getId();
                                                        if (junctionspinner.getSelectedItem().toString().equals("T junction")) {
                                                            tjunEfficiencyThread = new tjun_efficiency_thread(tjunction_ai_lane_1_in, tjunction_ai_lane_2_in, tjunction_ai_lane_3_in, tjunction_con_lane_1_in, tjunction_con_lane_2_in, tjunction_con_lane_3_in, SimulationActivity.this, simulationid, id, tjunpbar);
                                                            tjunEfficiencyThread.start();
                                                            conLane1AddThread = new t_lane_1_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane1_int_array, tjunction_con_lane_1_in, tjunction_ai_lane_1_in);
                                                            conLane1AddThread.start();
                                                            conLane1SubThread = new t_con_lane_1_sub_thread();
                                                            conLane1SubThread.start();
                                                            conLane2AddThread = new t_lane_2_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane2_int_array, tjunction_con_lane_2_in, tjunction_ai_lane_2_in);
                                                            conLane2AddThread.start();
                                                            conLane2SubThread = new t_con_lane_2_sub_thread();
                                                            conLane2SubThread.start();
                                                            conLane3AddThread = new t_lane_3_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane3_int_array, tjunction_con_lane_3_in, tjunction_ai_lane_3_in);
                                                            conLane3AddThread.start();
                                                            conLane3SubThread = new t_con_lane_3_sub_thread();
                                                            conLane3SubThread.start();
                                                            aiLane1SubThread = new t_ai_lane_1_sub_thread();
                                                            aiLane1SubThread.start();
                                                            aiLane2SubThread = new t_ai_lane_2_sub_thread();
                                                            aiLane2SubThread.start();
                                                            aiLane3SubThread = new t_ai_lane_3_sub_thread();
                                                            aiLane3SubThread.start();
                                                            controlTrafficSequencingThread = new control_traffic_sequencing_thread(Integer.parseInt(green_Time), Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this, tjunction_con_light_1, tjunction_con_light_2, tjunction_con_light_3);
                                                            controlTrafficSequencingThread.start();
                                                            aiTrafficSequencingThread = new ai_traffic_sequencing_thread(Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this);
                                                            aiTrafficSequencingThread.start();
                                                        } else {
                                                            roundaEfficiencyThread = new rounda_efficiency_thread(roundabout_ai_lane_1_in, roundabout_ai_lane_2_in, roundabout_ai_lane_3_in, roundabout_ai_lane_4_in, roundabout_con_lane_1_in, roundabout_con_lane_2_in, roundabout_con_lane_3_in, roundabout_con_lane_4_in, SimulationActivity.this, simulationid, id, roundapbar);
                                                            roundaEfficiencyThread.start();
                                                            roundaLane1AddThread = new rounda_lane_1_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane1_int_array, roundabout_con_lane_1_in, roundabout_ai_lane_1_in);
                                                            roundaLane1AddThread.start();
                                                            roundaConLane1SubThread = new rounda_con_lane_1_sub_thread();
                                                            roundaConLane1SubThread.start();
                                                            roundaLane2AddThread = new rounda_lane_2_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane2_int_array, roundabout_con_lane_2_in, roundabout_ai_lane_2_in);
                                                            roundaLane2AddThread.start();
                                                            roundaConLane2SubThread = new rounda_con_lane_2_sub_thread();
                                                            roundaConLane2SubThread.start();
                                                            roundaLane3AddThread = new rounda_lane_3_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane3_int_array, roundabout_con_lane_3_in, roundabout_ai_lane_3_in);
                                                            roundaLane3AddThread.start();
                                                            roundaConLane3SubThread = new rounda_con_lane_3_sub_thread();
                                                            roundaConLane3SubThread.start();
                                                            roundaLane4AddThread = new rounda_lane_4_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane4_int_array, roundabout_con_lane_4_in, roundabout_ai_lane_4_in);
                                                            roundaLane4AddThread.start();
                                                            roundaConLane4SubThread = new rounda_con_lane_4_sub_thread();
                                                            roundaConLane4SubThread.start();
                                                            roundaAiLane1SubThread = new rounda_ai_lane_1_sub_thread();
                                                            roundaAiLane1SubThread.start();
                                                            roundaAiLane2SubThread = new rounda_ai_lane_2_sub_thread();
                                                            roundaAiLane2SubThread.start();
                                                            roundaAiLane3SubThread = new rounda_ai_lane_3_sub_thread();
                                                            roundaAiLane3SubThread.start();
                                                            roundaAiLane4SubThread = new rounda_ai_lane_4_sub_thread();
                                                            roundaAiLane4SubThread.start();
                                                            controlTrafficSequencingThread = new control_traffic_sequencing_thread(Integer.parseInt(green_Time), Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this, roundabout_con_light_1, roundabout_con_light_2, roundabout_con_light_3, roundabout_con_light_4);
                                                            controlTrafficSequencingThread.start();
                                                            aiTrafficSequencingThread = new ai_traffic_sequencing_thread(Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this);
                                                            aiTrafficSequencingThread.start();
                                                        }

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(SimulationActivity.this, "Failed to start simulation", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        } else {
                                            POJO_simulation simulation =
                                                    new POJO_simulation(id, junctionspinner.getSelectedItem().toString(), densityspinner.getSelectedItem().toString(),
                                                            green_Time, orange_Time, addition_Time, "", "",
                                                            "", "", "", start_time, "", timeInMilliseconds, "", "", "", "");
                                            db.collection("Users").document(id).collection("Simulations").add(simulation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    simulationid = documentReference.getId();
                                                    if (junctionspinner.getSelectedItem().toString().equals("T junction")) {
                                                        tjunEfficiencyThread = new tjun_efficiency_thread(tjunction_ai_lane_1_in, tjunction_ai_lane_2_in, tjunction_ai_lane_3_in, tjunction_con_lane_1_in, tjunction_con_lane_2_in, tjunction_con_lane_3_in, SimulationActivity.this, simulationid, id, tjunpbar);
                                                        tjunEfficiencyThread.start();
                                                        conLane1AddThread = new t_lane_1_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane1_int_array, tjunction_con_lane_1_in, tjunction_ai_lane_1_in);
                                                        conLane1AddThread.start();
                                                        conLane1SubThread = new t_con_lane_1_sub_thread();
                                                        conLane1SubThread.start();
                                                        conLane2AddThread = new t_lane_2_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane2_int_array, tjunction_con_lane_2_in, tjunction_ai_lane_2_in);
                                                        conLane2AddThread.start();
                                                        conLane2SubThread = new t_con_lane_2_sub_thread();
                                                        conLane2SubThread.start();
                                                        conLane3AddThread = new t_lane_3_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane3_int_array, tjunction_con_lane_3_in, tjunction_ai_lane_3_in);
                                                        conLane3AddThread.start();
                                                        conLane3SubThread = new t_con_lane_3_sub_thread();
                                                        conLane3SubThread.start();
                                                        aiLane1SubThread = new t_ai_lane_1_sub_thread();
                                                        aiLane1SubThread.start();
                                                        aiLane2SubThread = new t_ai_lane_2_sub_thread();
                                                        aiLane2SubThread.start();
                                                        aiLane3SubThread = new t_ai_lane_3_sub_thread();
                                                        aiLane3SubThread.start();
                                                        controlTrafficSequencingThread = new control_traffic_sequencing_thread(Integer.parseInt(green_Time), Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this, tjunction_con_light_1, tjunction_con_light_2, tjunction_con_light_3);
                                                        controlTrafficSequencingThread.start();
                                                        aiTrafficSequencingThread = new ai_traffic_sequencing_thread(Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this);
                                                        aiTrafficSequencingThread.start();
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
                                                            tjunction_ai_lane_1_in.setText(String.valueOf(rand_no));
                                                            rand_no = rand.nextInt(11);
                                                            tjunction_con_lane_2_in.setText(String.valueOf(rand_no));
                                                            tjunction_ai_lane_2_in.setText(String.valueOf(rand_no));
                                                            rand_no = rand.nextInt(11);
                                                            tjunction_con_lane_3_in.setText(String.valueOf(rand_no));
                                                            tjunction_ai_lane_3_in.setText(String.valueOf(rand_no));
                                                        }
                                                    } else {

                                                        roundaEfficiencyThread = new rounda_efficiency_thread(roundabout_ai_lane_1_in, roundabout_ai_lane_2_in, roundabout_ai_lane_3_in, roundabout_ai_lane_4_in, roundabout_con_lane_1_in, roundabout_con_lane_2_in, roundabout_con_lane_3_in, roundabout_con_lane_4_in, SimulationActivity.this, simulationid, id, roundapbar);
                                                        roundaEfficiencyThread.start();
                                                        roundaLane1AddThread = new rounda_lane_1_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane1_int_array, roundabout_con_lane_1_in, roundabout_ai_lane_1_in);
                                                        roundaLane1AddThread.start();
                                                        roundaConLane1SubThread = new rounda_con_lane_1_sub_thread();
                                                        roundaConLane1SubThread.start();
                                                        roundaLane2AddThread = new rounda_lane_2_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane2_int_array, roundabout_con_lane_2_in, roundabout_ai_lane_2_in);
                                                        roundaLane2AddThread.start();
                                                        roundaConLane2SubThread = new rounda_con_lane_2_sub_thread();
                                                        roundaConLane2SubThread.start();
                                                        roundaLane3AddThread = new rounda_lane_3_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane3_int_array, roundabout_con_lane_3_in, roundabout_ai_lane_3_in);
                                                        roundaLane3AddThread.start();
                                                        roundaConLane3SubThread = new rounda_con_lane_3_sub_thread();
                                                        roundaConLane3SubThread.start();
                                                        roundaLane4AddThread = new rounda_lane_4_add_thread(addition_Time, densityspinner.getSelectedItem().toString(), lane4_int_array, roundabout_con_lane_4_in, roundabout_ai_lane_4_in);
                                                        roundaLane4AddThread.start();
                                                        roundaConLane4SubThread = new rounda_con_lane_4_sub_thread();
                                                        roundaConLane4SubThread.start();
                                                        roundaAiLane1SubThread = new rounda_ai_lane_1_sub_thread();
                                                        roundaAiLane1SubThread.start();
                                                        roundaAiLane2SubThread = new rounda_ai_lane_2_sub_thread();
                                                        roundaAiLane2SubThread.start();
                                                        roundaAiLane3SubThread = new rounda_ai_lane_3_sub_thread();
                                                        roundaAiLane3SubThread.start();
                                                        roundaAiLane4SubThread = new rounda_ai_lane_4_sub_thread();
                                                        roundaAiLane4SubThread.start();
                                                        controlTrafficSequencingThread = new control_traffic_sequencing_thread(Integer.parseInt(green_Time), Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this, roundabout_con_light_1, roundabout_con_light_2, roundabout_con_light_3, roundabout_con_light_4);
                                                        controlTrafficSequencingThread.start();
                                                        aiTrafficSequencingThread = new ai_traffic_sequencing_thread(Integer.parseInt(orange_Time), junctionspinner.getSelectedItem().toString(), SimulationActivity.this);
                                                        aiTrafficSequencingThread.start();
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
                                                            roundabout_ai_lane_1_in.setText(String.valueOf(rand_no));
                                                            rand_no = rand.nextInt(11);
                                                            roundabout_con_lane_2_in.setText(String.valueOf(rand_no));
                                                            roundabout_ai_lane_2_in.setText(String.valueOf(rand_no));
                                                            rand_no = rand.nextInt(11);
                                                            roundabout_con_lane_3_in.setText(String.valueOf(rand_no));
                                                            roundabout_ai_lane_3_in.setText(String.valueOf(rand_no));
                                                            rand_no = rand.nextInt(11);
                                                            roundabout_con_lane_4_in.setText(String.valueOf(rand_no));
                                                            roundabout_ai_lane_4_in.setText(String.valueOf(rand_no));
                                                        }
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SimulationActivity.this, "Failed to start simulation", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SimulationActivity.this, "Failed to start simulation", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SimulationActivity.this, "Failed to start simulation", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SimulationActivity.this, "Failed to start simulation", Toast.LENGTH_SHORT).show();
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
        startsim.setVisibility(View.VISIBLE);
        stopsim.setVisibility(View.GONE);
        sim_settings.setVisibility(View.VISIBLE);
        j_selection_layout.setVisibility(View.VISIBLE);
        d_selection_layout.setVisibility(View.VISIBLE);
        runningLabel.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            case R.id.account:
                stop_simulation();
                startActivity(new Intent(SimulationActivity.this, AccountSettingsActivity.class));
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

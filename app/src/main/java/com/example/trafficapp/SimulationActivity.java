package com.example.trafficapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class SimulationActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView back;
    private Button startsim, stopsim;
    private LinearLayout tjunction_layout, roundabout_layout;
    private TextView tjunction_con_light_1, tjunction_con_light_2, tjunction_con_light_3, tjunction_ai_light_1, tjunction_ai_light_2, tjunction_ai_light_3, tjunction_con_lane_1_in, tjunction_con_lane_2_out,
            tjunction_con_lane_3_in, tjunction_con_lane_4_out, tjunction_con_lane_5_in, tjunction_con_lane_6_out, tjunction_ai_lane_1_in, tjunction_ai_lane_2_out, tjunction_ai_lane_3_in, tjunction_ai_lane_4_out,
            tjunction_ai_lane_5_in, tjunction_ai_lane_6_out, roundabout_con_light_1, roundabout_con_light_2, roundabout_con_light_3, roundabout_con_light_4, roundabout_ai_light_1, roundabout_ai_light_2,
            roundabout_ai_light_3, roundabout_ai_light_4, roundabout_con_lane_1_in, roundabout_con_lane_2_out, roundabout_con_lane_3_in, roundabout_con_lane_4_out, roundabout_con_lane_5_in, roundabout_con_lane_6_out,
            roundabout_con_lane_7_in, roundabout_con_lane_8_out, roundabout_ai_lane_1_in, roundabout_ai_lane_2_out, roundabout_ai_lane_3_in, roundabout_ai_lane_4_out, roundabout_ai_lane_5_in, roundabout_ai_lane_6_out,
            roundabout_ai_lane_7_in, roundabout_ai_lane_8_out;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_sim);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tjunction_layout = findViewById(R.id.tjunction);
        roundabout_layout = findViewById(R.id.roundabout);
        tjunction_con_light_1 = findViewById(R.id.t_con_light_1);
        tjunction_con_light_2 = findViewById(R.id.t_con_light_2);
        tjunction_con_light_3 = findViewById(R.id.t_con_light_3);
        tjunction_ai_light_1 = findViewById(R.id.t_ai_light_1);
        tjunction_ai_light_2 = findViewById(R.id.t_ai_light_2);
        tjunction_ai_light_3 = findViewById(R.id.t_ai_light_3);
        tjunction_con_lane_1_in = findViewById(R.id.t_con_lane_1_in);
        tjunction_con_lane_2_out = findViewById(R.id.t_con_lane_2_out);
        tjunction_con_lane_3_in = findViewById(R.id.t_con_lane_3_in);
        tjunction_con_lane_4_out = findViewById(R.id.t_con_lane_4_out);
        tjunction_con_lane_5_in = findViewById(R.id.t_con_lane_5_in);
        tjunction_con_lane_6_out = findViewById(R.id.t_con_lane_6_out);
        tjunction_ai_lane_1_in = findViewById(R.id.t_ai_lane_1_in);
        tjunction_ai_lane_2_out = findViewById(R.id.t_ai_lane_2_out);
        tjunction_ai_lane_3_in = findViewById(R.id.t_ai_lane_3_in);
        tjunction_ai_lane_4_out = findViewById(R.id.t_ai_lane_4_out);
        tjunction_ai_lane_5_in = findViewById(R.id.t_ai_lane_5_in);
        tjunction_ai_lane_6_out = findViewById(R.id.t_ai_lane_6_out);
        roundabout_con_light_1 = findViewById(R.id.rounda_con_light_1);
        roundabout_con_light_2 = findViewById(R.id.rounda_con_light_2);
        roundabout_con_light_3 = findViewById(R.id.rounda_con_light_3);
        roundabout_con_light_4 = findViewById(R.id.rounda_con_light_4);
        roundabout_ai_light_1 = findViewById(R.id.rounda_ai_light_1);
        roundabout_ai_light_2 = findViewById(R.id.rounda_ai_light_2);
        roundabout_ai_light_3 = findViewById(R.id.rounda_ai_light_3);
        roundabout_ai_light_4 = findViewById(R.id.rounda_ai_light_4);
        roundabout_con_lane_1_in = findViewById(R.id.rounda_con_lane_1_in);
        roundabout_con_lane_2_out = findViewById(R.id.rounda_con_lane_2_out);
        roundabout_con_lane_3_in = findViewById(R.id.rounda_con_lane_3_in);
        roundabout_con_lane_4_out = findViewById(R.id.rounda_con_lane_4_out);
        roundabout_con_lane_5_in = findViewById(R.id.rounda_con_lane_5_in);
        roundabout_con_lane_6_out = findViewById(R.id.rounda_con_lane_6_out);
        roundabout_con_lane_7_in = findViewById(R.id.rounda_con_lane_7_in);
        roundabout_con_lane_8_out = findViewById(R.id.rounda_con_lane_8_out);
        roundabout_ai_lane_1_in = findViewById(R.id.rounda_ai_lane_1_in);
        roundabout_ai_lane_2_out = findViewById(R.id.rounda_ai_lane_2_out);
        roundabout_ai_lane_3_in = findViewById(R.id.rounda_ai_lane_3_in);
        roundabout_ai_lane_4_out = findViewById(R.id.rounda_ai_lane_4_out);
        roundabout_ai_lane_5_in = findViewById(R.id.rounda_ai_lane_5_in);
        roundabout_ai_lane_6_out = findViewById(R.id.rounda_ai_lane_6_out);
        roundabout_ai_lane_7_in = findViewById(R.id.rounda_ai_lane_7_in);
        roundabout_ai_lane_8_out = findViewById(R.id.rounda_ai_lane_8_out);
        startsim = findViewById(R.id.run_btn);
        stopsim = findViewById(R.id.stop_btn);
        back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Simulation");
        // tjunction_con_light_1.setBackgroundResource(R.drawable.traffic_light_green);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SimulationActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final Spinner junctionspinner = findViewById(R.id.junction_type_spin);
        ArrayAdapter<CharSequence> junctionSpinneradapter = ArrayAdapter.createFromResource(this, R.array.junctionTypes, R.layout.spinner_item);
        junctionSpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        junctionspinner.setAdapter(junctionSpinneradapter);
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
                startsim.setVisibility(View.GONE);
                stopsim.setVisibility(View.VISIBLE);
            }
        });
        stopsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsim.setVisibility(View.VISIBLE);
                stopsim.setVisibility(View.GONE);
            }
        });

        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationActivity.this, LoginActivity.class));
        }

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
                auth.signOut();
                startActivity(new Intent(SimulationActivity.this, LoginActivity.class));
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

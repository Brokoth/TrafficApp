package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class SimulationActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView back,tjunction_con,tjunction_AI,rounda_con,rounda_ai;
    private Button startsim,stopsim;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_sim);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tjunction_con = findViewById(R.id.tjunction_control);
        tjunction_AI = findViewById(R.id.tjunction_ai);
        rounda_con = findViewById(R.id.roundabout_control);
        rounda_ai = findViewById(R.id.roundabout_ai);
        startsim = findViewById(R.id.run_btn);
        stopsim = findViewById(R.id.stop_btn);
        back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Simulation");
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


                if ("T junction".equals(junctionspinner.getSelectedItem().toString())){
                    tjunction_con.setVisibility(View.VISIBLE);
                    tjunction_AI.setVisibility(View.VISIBLE);
                    rounda_con.setVisibility(View.GONE);
                    rounda_ai.setVisibility(View.GONE);
              }
                else{
                    tjunction_con.setVisibility(View.GONE);
                    tjunction_AI.setVisibility(View.GONE);
                    rounda_con.setVisibility(View.VISIBLE);
                    rounda_ai.setVisibility(View.VISIBLE);
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

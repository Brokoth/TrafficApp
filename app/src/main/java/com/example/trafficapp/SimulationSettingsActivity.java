package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SimulationSettingsActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView cust_vids, back;
    private FloatingActionButton save;
    private FirebaseFirestore db;
    private LinearLayout custom_timing;
    private EditText red_value, orange_value, green_value, subtraction_time, addition_time;
    static Spinner timingspinner = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.simulation_settings_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_sim_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Simulation Settings");
        back = toolbar.findViewById(R.id.back);
        red_value = findViewById(R.id.red_time);
        orange_value = findViewById(R.id.orange_time);
        green_value = findViewById(R.id.green_time);
        subtraction_time = findViewById(R.id.clear_time);
        addition_time = findViewById(R.id.addition_time);
        custom_timing = findViewById(R.id.custom_timing_layout);
        cust_vids = findViewById(R.id.custom_traffic);
        save = findViewById(R.id.save_btn);
        timingspinner = findViewById(R.id.timing_type_spin);
        ArrayAdapter<CharSequence> timingSpinneradapter = ArrayAdapter.createFromResource(this, R.array.timingTypes, R.layout.spinner_item);
        timingSpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timingspinner.setAdapter(timingSpinneradapter);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationSettingsActivity.this, LoginActivity.class));
        } else {
            db.collection("Users").document(uid).collection("Simulation_Settings")
                    .document("current").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("settingsType").equals("Custom")) {
                        timingspinner.setSelection(1);
                        custom_timing.setVisibility(View.VISIBLE);
                        red_value.setText(documentSnapshot.getString("redTime"));
                        green_value.setText(documentSnapshot.getString("greenTime"));
                        orange_value.setText(documentSnapshot.getString("orangeTime"));
                        subtraction_time.setText(documentSnapshot.getString("subtractionTime"));
                        addition_time.setText(documentSnapshot.getString("additionTime"));
                    } else {
                        timingspinner.setSelection(0);
                        custom_timing.setVisibility(View.GONE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        timingspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if ("Default".equals(timingspinner.getSelectedItem().toString())) {
                    custom_timing.setVisibility(View.GONE);

                } else {
                    custom_timing.setVisibility(View.VISIBLE);
                    db.collection("Users").document(uid).collection("Simulation_Settings")
                            .document("current").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                red_value.setText(documentSnapshot.getString("redTime"));
                                green_value.setText(documentSnapshot.getString("greenTime"));
                                orange_value.setText(documentSnapshot.getString("orangeTime"));
                                subtraction_time.setText(documentSnapshot.getString("subtractionTime"));
                                addition_time.setText(documentSnapshot.getString("additionTime"));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SimulationSettingsActivity.this,
                        SimulationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cust_vids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SimulationSettingsActivity.this,
                        CustomTrafficActivity.class);
                startActivity(intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timingspinner.getSelectedItem().toString().equals("Default")) {
                    db.collection("Users").document(uid).collection("Simulation_Settings")
                            .document("current").update("settingsType", "Default")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SimulationSettingsActivity.this, "Settings Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SimulationSettingsActivity.this, "Failed to Update. Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                if (TextUtils.isEmpty(red_value.getText().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the duration time for red light", Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(orange_value.getText().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the duration time for orange light", Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(green_value.getText().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the duration time for green light", Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(addition_time.getText().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the time between density increases", Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(subtraction_time.getText().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the time between density decreases", Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                    return;
                } else {
//POJO_simulation_settings settings = new POJO_simulation_settings();
                    db.collection("Users").document(uid).collection("Simulation_Settings")
                            .document("current").update("redTime", red_value.getText().toString(), "greenTime", green_value.getText().toString(), "orangeTime", orange_value.getText().toString(), "additionTime", addition_time.getText().toString(), "subtractionTime", subtraction_time.getText().toString(), "settingsType", "Custom")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SimulationSettingsActivity.this, "Settings Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SimulationSettingsActivity.this, "Failed to Update. Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationSettingsActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SimulationSettingsActivity.this,
                SimulationActivity.class);
        startActivity(intent);
        finish();
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
                startActivity(new Intent(SimulationSettingsActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

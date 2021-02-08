package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private Button defaults;
    private ProgressBar settingsBar;
    private FirebaseFirestore db;
    private Spinner orangespinner,addspinner;
    private EditText orange_value, green_value, addition_time;

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
        orangespinner = findViewById(R.id.orange_time_spin);
        settingsBar = findViewById(R.id.settings_progressBar);
        green_value = findViewById(R.id.green_time);
        addspinner = findViewById(R.id.add_time_spin);
        cust_vids = findViewById(R.id.custom_traffic);
        save = findViewById(R.id.save_btn);
        ArrayAdapter<CharSequence> orangeSpinneradapter = ArrayAdapter.createFromResource(this, R.array.orangeTimings, R.layout.sim_settimgs_spinner_item);
        orangeSpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orangespinner.setAdapter(orangeSpinneradapter);
        ArrayAdapter<CharSequence> addSpinneradapter = ArrayAdapter.createFromResource(this, R.array.densityTimings, R.layout.sim_settimgs_spinner_item);
        addSpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addspinner.setAdapter(addSpinneradapter);
     //   defaults = findViewById(R.id.defaults_btn);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationSettingsActivity.this, LoginActivity.class));
        } else {
            db.collection("Users").document(uid).collection("Simulation_Settings")
                    .document("timings").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    green_value.setText(documentSnapshot.getString("greenTime"));
                    addspinner.setSelection(addSpinneradapter.getPosition( documentSnapshot.getString("additionTime")));
                    orangespinner.setSelection(orangeSpinneradapter.getPosition( documentSnapshot.getString("orangeTime")));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SimulationSettingsActivity.this, "Failed to Retrieve user data. Try Again Later", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        defaults.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                green_value.setText("10");
//                orange_value.setText("3");
//                addition_time.setText("5");
//            }
//        });

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
                settingsBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(orangespinner.getSelectedItem().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the duration time for orange light", Toast.LENGTH_SHORT).show();
                    settingsBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(green_value.getText().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the duration time for green light", Toast.LENGTH_SHORT).show();
                    settingsBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(addspinner.getSelectedItem().toString())) {
                    Toast.makeText(SimulationSettingsActivity.this, "Set the time between density increases", Toast.LENGTH_SHORT).show();
                    settingsBar.setVisibility(View.GONE);
                    return;
                } else {
                    db.collection("Users").document(uid).collection("Simulation_Settings")
                            .document("timings").update("greenTime", green_value.getText().toString(), "orangeTime", orangespinner.getSelectedItem().toString(), "additionTime", addspinner.getSelectedItem().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SimulationSettingsActivity.this, "Settings Updated Successfully", Toast.LENGTH_SHORT).show();
                                    settingsBar.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SimulationSettingsActivity.this, "Failed to Update. Try Again Later", Toast.LENGTH_SHORT).show();
                            settingsBar.setVisibility(View.GONE);
                        }
                    });
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
            case R.id.account:
                startActivity(new Intent(SimulationSettingsActivity.this, AccountSettingsActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

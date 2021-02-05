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
import android.widget.Button;
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
import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;

public class SimulationSettingsActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView cust_vids, back;
    private FloatingActionButton save;
    private Button defaults;
    private FirebaseFirestore db;
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
        orange_value = findViewById(R.id.orange_time);
        green_value = findViewById(R.id.green_time);
        addition_time = findViewById(R.id.addition_time);
        cust_vids = findViewById(R.id.custom_traffic);
        save = findViewById(R.id.save_btn);
        defaults = findViewById(R.id.defaults_btn);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationSettingsActivity.this, LoginActivity.class));
        } else {
            db.collection("Users").document(uid).collection("Simulation_Settings")
                    .document("timings").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    green_value.setText(documentSnapshot.getString("greenTime"));
                    orange_value.setText(documentSnapshot.getString("orangeTime"));
                    addition_time.setText(documentSnapshot.getString("additionTime"));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        defaults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                green_value.setText("10");
                orange_value.setText("3");
                addition_time.setText("5");
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
                if (TextUtils.isEmpty(orange_value.getText().toString())) {
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
                } else {
                    db.collection("Users").document(uid).collection("Simulation_Settings")
                            .document("timings").update("greenTime", green_value.getText().toString(), "orangeTime", orange_value.getText().toString(), "additionTime", addition_time.getText().toString())
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

package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomTrafficActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button generate;
    private ProgressBar generate_progress_bar;
    private EditText linkOne, linkTwo, linkThree, linkFour, frameValue;
    private ImageView back;
    private FirebaseFirestore db;
    private video_saving_and_car_detection_thread videoSavingAndCarDetectionThread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.custom_traffic_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_custom_traffic);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Custom Traffic");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        linkOne = findViewById(R.id.link1);
        generate_progress_bar = findViewById(R.id.generate_btn_progress_bar);
        linkTwo = findViewById(R.id.link2);
        frameValue = findViewById(R.id.frame_difference);
        linkThree = findViewById(R.id.link3);
        linkFour = findViewById(R.id.link4);
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(CustomTrafficActivity.this, LoginActivity.class));
        } else {
            db.collection("Users").document(uid).collection("Simulation_Settings")
                    .document("links").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    linkOne.setText(documentSnapshot.getString("link_one"));
                    linkTwo.setText(documentSnapshot.getString("link_two"));
                    linkThree.setText(documentSnapshot.getString("link_three"));
                    linkFour.setText(documentSnapshot.getString("link_four"));
                    frameValue.setText(documentSnapshot.getString("frame_interval"));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        back = toolbar.findViewById(R.id.back);
        generate = findViewById(R.id.generate_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomTrafficActivity.this,
                        SimulationSettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate.setVisibility(View.GONE);
                generate_progress_bar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(linkOne.getText().toString())) {
                    Toast.makeText(CustomTrafficActivity.this, "Set the lane one link", Toast.LENGTH_SHORT).show();
                    generate.setVisibility(View.VISIBLE);
                    generate_progress_bar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(linkTwo.getText().toString())) {
                    Toast.makeText(CustomTrafficActivity.this, "Set the lane two link", Toast.LENGTH_SHORT).show();
                    generate.setVisibility(View.VISIBLE);
                    generate_progress_bar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(linkThree.getText().toString())) {
                    Toast.makeText(CustomTrafficActivity.this, "Set the lane three link", Toast.LENGTH_SHORT).show();
                    generate.setVisibility(View.VISIBLE);
                    generate_progress_bar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(linkFour.getText().toString())) {
                    Toast.makeText(CustomTrafficActivity.this, "Set the lane four link", Toast.LENGTH_SHORT).show();
                    generate.setVisibility(View.VISIBLE);
                    generate_progress_bar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(frameValue.getText().toString())) {
                    Toast.makeText(CustomTrafficActivity.this, "Set the frame interval", Toast.LENGTH_SHORT).show();
                    generate.setVisibility(View.VISIBLE);
                    generate_progress_bar.setVisibility(View.GONE);
                    return;
                } else {
                    db.collection("Users").document(uid).collection("Simulation_Settings")
                            .document("links").update("link_one", linkOne.getText().toString(), "link_two", linkTwo.getText().toString(), "link_three", linkThree.getText().toString(), "link_four", linkFour.getText().toString(), "frame_interval", frameValue.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    videoSavingAndCarDetectionThread = new video_saving_and_car_detection_thread(generate,generate_progress_bar,frameValue.getText().toString(), linkOne.getText().toString(), linkTwo.getText().toString(), linkThree.getText().toString(), linkFour.getText().toString(), CustomTrafficActivity.this, CustomTrafficActivity.this.getFilesDir().toString());
                                    videoSavingAndCarDetectionThread.start();
                                    Toast.makeText(CustomTrafficActivity.this, "GENERATING....", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            generate.setVisibility(View.VISIBLE);
                            generate_progress_bar.setVisibility(View.GONE);
                            Toast.makeText(CustomTrafficActivity.this, "Failed to generate values. Try Again Later", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(CustomTrafficActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CustomTrafficActivity.this,
                SimulationSettingsActivity.class);
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
                startActivity(new Intent(CustomTrafficActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

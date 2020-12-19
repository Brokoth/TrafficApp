package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class SimulationActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_sim);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        final Spinner searchspinner = findViewById(R.id.junction_type_spin);
        ArrayAdapter<CharSequence> searchSpinneradapter = ArrayAdapter.createFromResource(this, R.array.junctionTypes, R.layout.spinner_item);
        searchSpinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchspinner.setAdapter(searchSpinneradapter);

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

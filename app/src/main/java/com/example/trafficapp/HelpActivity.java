package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HelpActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView back;
    private Button alg, cal, stats;
    private NestedScrollView myscroll;
    private FloatingActionButton totop;
    private TextView algtxt, caltxt, statstxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back = toolbar.findViewById(R.id.back);
        alg = findViewById(R.id.innerworks_btn);
        cal = findViewById(R.id.settings_btn);
        stats = findViewById(R.id.history_btn);
        algtxt = findViewById(R.id.simulation_documentation);
        caltxt = findViewById(R.id.callibration_documentation);
        statstxt = findViewById(R.id.history_documentation);
        myscroll = findViewById(R.id.scrollView);
        totop = findViewById(R.id.return_btn);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Documentation");
        alg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myscroll.smoothScrollTo(0, algtxt.getTop());

            }
        });
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myscroll.smoothScrollTo(0, caltxt.getTop());
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myscroll.smoothScrollTo(0, statstxt.getTop());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myscroll.smoothScrollTo(0, myscroll.getTop());
            }
        });

        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(HelpActivity.this, LoginActivity.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(HelpActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HelpActivity.this,
                HomeActivity.class);
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
                startActivity(new Intent(HelpActivity.this, LoginActivity.class));
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

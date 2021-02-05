package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HistoryActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView back;
    private HistoryAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference Cref = db.collection("Users");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_his);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("History");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(HistoryActivity.this, LoginActivity.class));
        }
        setUpRecyclerView();
        adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                final String SimulationId = documentSnapshot.getId();
                Intent intent = new Intent(HistoryActivity.this,
                        SimulationStatsActivity.class);
                intent.putExtra("SimulationID", SimulationId);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView()
    {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = Cref.document(id).collection("Simulations").orderBy("timemilli", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<POJO_simulation> options = new
                FirestoreRecyclerOptions.Builder<POJO_simulation>()
                .setQuery(query, POJO_simulation.class).
                        build();

        adapter = new HistoryAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.history_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteSimulation(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(HistoryActivity.this, LoginActivity.class));
        }
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HistoryActivity.this,
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
            case R.id.account:
                startActivity(new Intent(HistoryActivity.this, AccountSettingsActivity.class));
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

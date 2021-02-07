package com.example.trafficapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SimulationStatsActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView back;
    private TextView junctiontxt, densitytxt, greentxt, orangetxt, addtxt, link1txt, link2txt, link3txt, link4txt, frametxt;
    private LinearLayout linksLayout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LineGraphSeries<DataPoint> con_series, ai_series;
    private LineChart lineChart;
    private String TAG = "SimulationStatsActivitydebug";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_stats_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar_sim_stats);
        greentxt = findViewById(R.id.greentime);
        orangetxt = findViewById(R.id.orangetime);
        addtxt = findViewById(R.id.additiontime);
        link1txt = findViewById(R.id.linkone);
        link2txt = findViewById(R.id.linktwo);
        link3txt = findViewById(R.id.linkthree);
        link4txt = findViewById(R.id.linkfour);
        frametxt = findViewById(R.id.frameinterval);
        linksLayout = findViewById(R.id.links);
        junctiontxt = findViewById(R.id.junction);
        densitytxt = findViewById(R.id.density);
        lineChart = findViewById(R.id.graph);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Simulation Stats");
        String simulationID = getIntent().getStringExtra("SimulationID");
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Users").document(id).collection("Simulations").
                document(simulationID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                orangetxt.setText(documentSnapshot.getString("orangeTime"));
                greentxt.setText(documentSnapshot.getString("greenTime"));
                addtxt.setText(documentSnapshot.getString("additionTime"));
                junctiontxt.setText(documentSnapshot.getString("junctionType"));
                densitytxt.setText(documentSnapshot.getString("densityType"));
                String aicoords = documentSnapshot.getString("ai_coords");
                String concoords = documentSnapshot.getString("con_coords");
                if (documentSnapshot.getString("link_one").equals("") || documentSnapshot.getString("link_two").equals("") || documentSnapshot.getString("link_three").equals("")
                        || documentSnapshot.getString("link_four").equals("") || documentSnapshot.getString("frame_interval").equals("")) {
                    linksLayout.setVisibility(View.GONE);
                } else {
                    link1txt.setText(documentSnapshot.getString("link_one"));
                    link2txt.setText(documentSnapshot.getString("link_two"));
                    link3txt.setText(documentSnapshot.getString("link_three"));
                    link4txt.setText(documentSnapshot.getString("link_four"));
                    frametxt.setText(documentSnapshot.getString("frame_interval"));
                }
                aicoords = aicoords.replaceAll("-$", "");
                concoords = concoords.replaceAll("-$", "");
                aicoords = aicoords.replaceAll("[()]", "");
                concoords = concoords.replaceAll("[()]", "");
                String[] aicoordsarray = aicoords.split("-");
                String[] concoordsarray = concoords.split("-");
                int arraylength = Array.getLength(aicoordsarray);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                DecimalFormat df = new DecimalFormat("####");
                String[] time_list = new String[arraylength];
                String[] ai_density_list = new String[arraylength];
                String[] con_density_list = new String[arraylength];

                for (int i = 0; i < arraylength; i++) {
                    String[] aisingular = aicoordsarray[i].split(",");
                    String[] consingular = concoordsarray[i].split(",");
                    time_list[i] = aisingular[1];
                    ai_density_list[i] = aisingular[0];
                    con_density_list[i] = consingular[0];
                }
                LineDataSet lineDataSet = new LineDataSet(dataValues1(arraylength, ai_density_list), "A.I Density Progression");
                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

                LineDataSet lineDataSet2 = new LineDataSet(dataValues2(arraylength, con_density_list), "Control Density Progression");
                dataSets.add(lineDataSet2);

                dataSets.add(lineDataSet);
                LineData data = new LineData(dataSets);
                Description description = new Description();
                description.setText("Average junction Traffic Density Comparison");
                int[] colors = new int[]{ Color.parseColor("#FF0000"),Color.parseColor("#0b6103")};
                String[] legendNames = {"Control Density Progression", "A.I Density Progression"};
                Legend legend = lineChart.getLegend();
                legend.setEnabled(true);
                LegendEntry[] legendEntries = new LegendEntry[2];
                for (int i=0; i<legendEntries.length;i++){
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colors[i];
                    entry.label = legendNames[i];
                    legendEntries[i] = entry;
                }
                legend.setCustom(legendEntries);
                legend.setTextSize(12);
                legend.setFormSize(12);
                lineDataSet.setLineWidth(3);
                lineDataSet.setColor(Color.parseColor("#0b6103"));
                lineDataSet.setCircleColor(Color.parseColor("#0b6103"));
                lineDataSet.setValueTextSize(10);
                lineDataSet2.setLineWidth(3);
                lineDataSet2.setColor(Color.parseColor("#FF0000"));
                lineDataSet2.setCircleColor(Color.parseColor("#FF0000"));
                lineDataSet2.setValueTextSize(10);
                lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(time_list));
                lineChart.animateXY(3000,  3000,Easing.Linear,Easing.Linear);
                lineChart.setHorizontalScrollBarEnabled(true);
                lineChart.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                lineChart.setNoDataText("No data retrieved. Try again later.");
                lineChart.setNoDataTextColor(Color.parseColor("#FF018786"));
                lineChart.setDrawBorders(true);
                lineChart.setBorderColor(Color.parseColor("#FF03DAC5"));
                lineChart.setBorderWidth(5);
                lineChart.setDescription(description);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SimulationStatsActivity.this, "Failed to retrieve simulation stats", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SimulationStatsActivity.this,
                        HistoryActivity.class);
                startActivity(intent);
            }
        });
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationStatsActivity.this, LoginActivity.class));
        }
    }
    private ArrayList<Entry> dataValues1(int arraylength, String[] vals) {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        for (int i = 0; i < arraylength; i++) {
            dataVals.add(new Entry(i, Float.valueOf(vals[i])));
        }
        return dataVals;
    }

    private ArrayList<Entry> dataValues2(int arraylength, String[] vals) {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        for (int i = 0; i < arraylength; i++) {
            dataVals.add(new Entry(i, Float.valueOf(vals[i])));
        }
        return dataVals;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(SimulationStatsActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SimulationStatsActivity.this,
                HistoryActivity.class);
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
                startActivity(new Intent(SimulationStatsActivity.this, AccountSettingsActivity.class));
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

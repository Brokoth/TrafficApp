//package com.example.afresports;
//
//public class YourEventsAdapter {
//
//
//}
package com.example.trafficapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HistoryAdapter extends FirestoreRecyclerAdapter<POJO_simulation, HistoryAdapter.YourHistoryHolder> {
    private HistoryAdapter.OnItemClickListener listener;
    final private String tag = "history_adapter";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HistoryAdapter(@NonNull FirestoreRecyclerOptions<POJO_simulation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final YourHistoryHolder yourHistoryHolder, int i, @NonNull final POJO_simulation pojoSimulationCustom) {
        String[] time_str_array = pojoSimulationCustom.getStart_time().split(" ");
        yourHistoryHolder.junction.setText(pojoSimulationCustom.getJunctionType());
        yourHistoryHolder.density.setText(pojoSimulationCustom.getDensityType());
        yourHistoryHolder.time_value.setText(time_str_array[0]);
        if (!pojoSimulationCustom.getAi_efficiency().equals("") && !pojoSimulationCustom.getControl_efficiency().equals("")) {
            float efficiency_of_ai = ((Float.parseFloat(pojoSimulationCustom.getAi_efficiency()) - Float.parseFloat(pojoSimulationCustom.getControl_efficiency())) / Float.parseFloat(pojoSimulationCustom.getControl_efficiency()) * 100);
            if (efficiency_of_ai < 0) {
                efficiency_of_ai = efficiency_of_ai * -1;
                yourHistoryHolder.efficiency.setText(String.valueOf(efficiency_of_ai));
                if (Float.parseFloat(pojoSimulationCustom.getAi_efficiency()) < Float.parseFloat(pojoSimulationCustom.getControl_efficiency())) {
                    yourHistoryHolder.efficiency.setTextColor(Color.parseColor("#0b6103"));
                    yourHistoryHolder.symbol.setTextColor(Color.parseColor("#0b6103"));
                } else {
                    yourHistoryHolder.efficiency.setTextColor(Color.parseColor("#FF0000"));
                    yourHistoryHolder.symbol.setTextColor(Color.parseColor("#FF0000"));
                }
            }
            else {
                yourHistoryHolder.efficiency.setText(String.valueOf(efficiency_of_ai));
                if (Float.parseFloat(pojoSimulationCustom.getAi_efficiency()) < Float.parseFloat(pojoSimulationCustom.getControl_efficiency())) {
                    yourHistoryHolder.efficiency.setTextColor(Color.parseColor("#0b6103"));
                    yourHistoryHolder.symbol.setTextColor(Color.parseColor("#0b6103"));
                } else {
                    yourHistoryHolder.efficiency.setTextColor(Color.parseColor("#FF0000"));
                    yourHistoryHolder.symbol.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        }
    }

    @NonNull
    @Override
    public YourHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.
                layout.history_item, parent, false
        );
        return new YourHistoryHolder(v);
    }

    class YourHistoryHolder extends RecyclerView.ViewHolder {
        TextView junction, density, efficiency, time_value, symbol;

        //Button delete;
        public YourHistoryHolder(@NonNull View itemView) {
            super(itemView);
            junction = itemView.findViewById(R.
                    id.junction_type);
            density = itemView.findViewById(R.
                    id.density_type);
            efficiency = itemView.findViewById(R.
                    id.ai_efficiency);
            time_value = itemView.findViewById(R.
                    id.time);
            symbol = itemView.findViewById(R.
                    id.percentage_symbol);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener
                            != null) {
                        listener.OnItemClick(getSnapshots().getSnapshot(position)
                                , position);
                    }

                }
            });

        }
    }

    public void deleteSimulation(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();

    }


    public interface OnItemClickListener {
        void OnItemClick(
                DocumentSnapshot documentSnapshot,
                int position);

    }

    public void setOnItemClickListener(HistoryAdapter.OnItemClickListener
                                               listener) {
        this.listener = listener;

    }
}

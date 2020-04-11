package com.example.trip_plan_budget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.model.flight.Carrier;

import java.util.List;

public class FlightCarrierAdapter extends RecyclerView.Adapter<FlightCarrierAdapter.CarrierViewHolder> {
    private List<Carrier> carriers;
    private Context context;

    public FlightCarrierAdapter(List<Carrier> carriers, Context context) {
        this.carriers = carriers;
        this.context = context;
    }

    @NonNull
    @Override
    public CarrierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarrierViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_carrier_text, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarrierViewHolder holder, int position) {
        holder.bind(carriers.get(position));
    }

    @Override
    public int getItemCount() {
        return carriers.size();
    }

    class CarrierViewHolder extends RecyclerView.ViewHolder {

        CarrierViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Carrier carrier) {
            TextView textView = itemView.findViewById(R.id.flight_carrier_text_template);
            textView.setText(carrier.getName());
        }
    }
}

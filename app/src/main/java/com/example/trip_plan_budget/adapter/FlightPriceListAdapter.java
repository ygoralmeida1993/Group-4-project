package com.example.trip_plan_budget.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.model.flight.PriceModel;

import java.util.List;

public class FlightPriceListAdapter extends RecyclerView.Adapter<FlightPriceListAdapter.FlightAdapterViewHolder> {

    private List<PriceModel> priceModelList;

    public FlightPriceListAdapter(List<PriceModel> priceModelList) {
        this.priceModelList = priceModelList;
    }

    @NonNull
    @Override
    public FlightAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapterViewHolder holder, int position) {
        holder.bind(priceModelList.get(position));
    }


    @Override
    public int getItemCount() {
        return priceModelList.size();
    }

    class FlightAdapterViewHolder extends RecyclerView.ViewHolder {

        public FlightAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(PriceModel model) {

        }
    }
}

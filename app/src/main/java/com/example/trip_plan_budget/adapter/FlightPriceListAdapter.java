package com.example.trip_plan_budget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.interfaces.callback.OnFlightPriceListClickListener;
import com.example.trip_plan_budget.model.flight.Carrier;
import com.example.trip_plan_budget.model.flight.PriceModel;

import java.util.List;

public class FlightPriceListAdapter extends RecyclerView.Adapter<FlightPriceListAdapter.FlightAdapterViewHolder> {
    private OnFlightPriceListClickListener listener;
    private List<PriceModel> priceModelList;
    private Context context;
    private List<Carrier> carriers;

    public FlightPriceListAdapter(Context context, List<PriceModel> priceModelList, List<Carrier> carriers, OnFlightPriceListClickListener listener) {
        this.priceModelList = priceModelList;
        this.context = context;
        this.carriers = carriers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FlightAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FlightAdapterViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_flight_price_list_item, parent, false));
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

        FlightAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(PriceModel model) {
            itemView.setOnClickListener(v -> listener.onCLick(model));
            TextView minPrice = itemView.findViewById(R.id.flight_min_price);
            TextView route = itemView.findViewById(R.id.flight_route);
            RecyclerView recyclerView = itemView.findViewById(R.id.flight_carrier_recycler);
            recyclerView.setAdapter(new FlightCarrierAdapter(carriers, context));
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            minPrice.setText(model.getPrice() + " C$");
            route.setText(model.isDirectRoute() ? "Direct" : "Indirect");

        }
    }
}

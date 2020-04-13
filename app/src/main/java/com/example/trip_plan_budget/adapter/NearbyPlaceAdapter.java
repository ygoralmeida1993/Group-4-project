package com.example.trip_plan_budget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.databinding.LayoutNearbyPlaceItemBinding;
import com.example.trip_plan_budget.interfaces.callback.NearbyPlaceItemClickListener;
import com.example.trip_plan_budget.model.places.PlaceModel;

import java.text.DecimalFormat;
import java.util.List;

public class NearbyPlaceAdapter extends RecyclerView.Adapter<NearbyPlaceAdapter.NearbyPlaceViewHolder> {

    private List<PlaceModel> places;
    private NearbyPlaceItemClickListener clickListener;

    public NearbyPlaceAdapter(List<PlaceModel> places, NearbyPlaceItemClickListener clickListener) {
        this.places = places;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NearbyPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NearbyPlaceViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_nearby_place_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyPlaceViewHolder holder, int position) {
        holder.bind(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class NearbyPlaceViewHolder extends RecyclerView.ViewHolder {
        private LayoutNearbyPlaceItemBinding binding;

        NearbyPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = LayoutNearbyPlaceItemBinding.bind(itemView);
        }

        void bind(PlaceModel model) {
            binding.placeName.setText(model.getName());
            double distance = model.getDist();
            DecimalFormat formatter = new DecimalFormat("0.00");
            if (distance > 1000) {
                distance /= 1609.34;
                binding.placeDistance.setText(String.format("%s miles", formatter.format(distance)));
            } else
                binding.placeDistance.setText(String.format("%s meters", (int) distance));
            binding.placeMap.setOnClickListener(v -> clickListener.onClick(model));
        }
    }
}

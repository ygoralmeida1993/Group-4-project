package com.example.trip_plan_budget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.interfaces.callback.PlaceSearchItemClickListener;

import java.util.List;

public class PlaceSearchAdapter extends RecyclerView.Adapter<PlaceSearchAdapter.PlaceSearchViewHolder> {
    private List<String> places;
    private PlaceSearchItemClickListener clickListener;

    public PlaceSearchAdapter(List<String> places, PlaceSearchItemClickListener clickListener) {
        this.places = places;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PlaceSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceSearchViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_place_search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceSearchViewHolder holder, int position) {
        holder.bind(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class PlaceSearchViewHolder extends RecyclerView.ViewHolder {

        PlaceSearchViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(String s) {
            ((TextView) itemView.findViewById(R.id.searchPlaceItem)).setText(s);
            itemView.setOnClickListener(v -> clickListener.onItemClick(s));
        }
    }
}

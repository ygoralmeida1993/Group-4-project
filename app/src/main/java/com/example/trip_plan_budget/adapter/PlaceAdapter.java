package com.example.trip_plan_budget.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.activity.MapsActivity;
import com.example.trip_plan_budget.model.PlaceDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {
    Context context;
    private List<PlaceDetailsModel> List = new ArrayList<>();
    private int addPosition;

    public PlaceAdapter(List<PlaceDetailsModel> List) {
        this.List = List;
    }

    @NonNull
    @Override
    public PlaceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceAdapter.MyViewHolder holder, final int position) {

        String place = "";
        place = List.get(position).getPlacename();
        String price = String.valueOf(List.get(position).getBudget());
        //if(place.isEmpty()){
        //holder.title.setText(" ");
        // }else{
        holder.title.setText(String.format("%s%s", place.substring(0, 1).toUpperCase(), place.substring(1)));
        Picasso.get().load(List.get(position).getImage()).into(holder.imageView);
        holder.price.setText(String.format("Budget: %s$", price));
        holder.map.setOnClickListener(v -> {

            Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
            Bundle b = new Bundle();
            double longitude = Double.parseDouble(List.get(position).getLongitude());
            double latitude = Double.parseDouble(List.get(position).getLatitude());
            intent.putExtra("long", longitude);
            intent.putExtra("lat", latitude);
            intent.putExtras(b);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView imageView, map;

        MyViewHolder(View view) {
            super(view);
            price = view.findViewById(R.id.price);
            map = view.findViewById(R.id.mapicon);
            title = view.findViewById(R.id.name);
            imageView = view.findViewById(R.id.iv);
        }
    }


}

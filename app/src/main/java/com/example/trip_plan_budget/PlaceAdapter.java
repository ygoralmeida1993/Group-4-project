package com.example.trip_plan_budget;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {
    private List<PlaceDetailsModel> List= new ArrayList<>();

    Context context;
    private int addPosition;

    public PlaceAdapter(List<PlaceDetailsModel> List){
        this.List=List;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,price;
        ImageView imageView,map;
        public MyViewHolder(View view) {
            super(view);
            price=(TextView) view.findViewById(R.id.price);
            map=(ImageView) view.findViewById(R.id.mapicon);
            title= (TextView) view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.iv);
        }
    }


    @Override
    public PlaceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customitem, parent, false);



        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final PlaceAdapter.MyViewHolder holder, final int position) {

        String place=List.get(position).getPlacename();
        String price= String.valueOf(List.get(position).getBudget());
        holder.title.setText(place.substring(0, 1).toUpperCase() + place.substring(1));
        Picasso.get().load(List.get(position).getImage()).into(holder.imageView);
        holder.price.setText("Budget: "+price);
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), GoogleMapsActivity.class);
                Bundle b = new Bundle();
                double longitude= Double.parseDouble(List.get(position).getLongitude());
                double latitude= Double.parseDouble(List.get(position).getLatitude())
                        ;                intent.putExtra("long",longitude );
                intent.putExtra("lat",latitude);
                intent.putExtras(b);

                holder.itemView.getContext().startActivity(intent);



            }
        });
    }


    @Override
    public int getItemCount()
    {
        return List.size();
    }


}

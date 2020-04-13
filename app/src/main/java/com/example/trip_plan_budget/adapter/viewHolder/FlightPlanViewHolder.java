package com.example.trip_plan_budget.adapter.viewHolder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.databinding.LayoutFlightPlanItemBinding;
import com.example.trip_plan_budget.interfaces.callback.FlightPlanCLickListener;
import com.example.trip_plan_budget.model.flight.FlightModel;

public class FlightPlanViewHolder extends RecyclerView.ViewHolder {
    private LayoutFlightPlanItemBinding binding;

    public FlightPlanViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = LayoutFlightPlanItemBinding.bind(itemView);
    }

    public void bind(FlightModel model, FlightPlanCLickListener cLickListener) {
        binding.getRoot().setOnClickListener(v -> cLickListener.onClick());

        String boarding = model.getDeparture().getPlaceId().split("-")[0] + "\n" + model.getDeparture().getPlaceName();
        binding.boardingPoint.setText(boarding);
        String landing = model.getLanding().getPlaceId().split("-")[0] + "\n" + model.getLanding().getPlaceName();
        binding.landingPoint.setText(landing);
        binding.departureDate.setText(model.getFrom());
        if (model.isRoundTrip()) {
            Drawable drawable = ResourcesCompat.getDrawable(Resources.getSystem(), R.drawable.ic_round_trip, null);
            binding.tripWay.setImageDrawable(drawable);
            binding.returnDate.setText(model.getTo());
        } else {
            binding.returnLayout.setVisibility(View.GONE);
        }

    }
}

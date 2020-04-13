package com.example.trip_plan_budget.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.adapter.PlanAdapter;
import com.example.trip_plan_budget.databinding.FragmentPlanBinding;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;

public class FlightPlanFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerPagingAdapter adapter;
    private FragmentPlanBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
//        binding = FragmentHomePlanBinding.inflate(inflater);
        recyclerView = view.findViewById(R.id.planRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        binding.planRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = PlanAdapter.getInstance().getFlightPlanAdapter(getContext(), getActivity());
//        binding.planRecycler.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) adapter.stopListening();
    }
}

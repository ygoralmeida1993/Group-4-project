package com.example.trip_plan_budget.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.adapter.PlanAdapter;
import com.example.trip_plan_budget.databinding.FragmentHomePlanBinding;
import com.example.trip_plan_budget.enumeration.PlanType;
import com.example.trip_plan_budget.service.AuthService;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.google.firebase.database.Query;

public class HomePlanFragment extends Fragment {

    private PlanType type;
    private FragmentHomePlanBinding binding;
    private Query query;
    private String uid;
    private FirebaseRecyclerPagingAdapter adapter;

    public static HomePlanFragment newInstance(PlanType type) {
        HomePlanFragment fragment = new HomePlanFragment();
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (PlanType) getArguments().getSerializable("type");
        uid = AuthService.getInstance().getID();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_plan, container, false);
        binding = FragmentHomePlanBinding.inflate(inflater);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (type) {
            case FLIGHT:
                adapter = PlanAdapter.getInstance(getActivity()).getFlightPlanAdapter();
                binding.planRecycler.setAdapter(adapter);
                break;
        }
    }
}

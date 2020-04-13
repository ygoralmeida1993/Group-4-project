package com.example.trip_plan_budget.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.trip_plan_budget.fragment.FlightPlanFragment;

public class HomeFragmentPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS = 4;

    public HomeFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FlightPlanFragment();
            case 2:
                return new FlightPlanFragment();
            case 3:
                return new FlightPlanFragment();
            default:
                return new FlightPlanFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }

}

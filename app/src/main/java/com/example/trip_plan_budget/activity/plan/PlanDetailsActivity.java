package com.example.trip_plan_budget.activity.plan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.databinding.ActivityPlanDetailsBinding;
import com.example.trip_plan_budget.enumeration.EnumUtil;
import com.example.trip_plan_budget.enumeration.PlanType;

public class PlanDetailsActivity extends AppCompatActivity {
    private ActivityPlanDetailsBinding binding;
    private PlanType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = EnumUtil.deserialize(PlanType.class).from(getIntent());
        if (type != null) {
            switch (type) {
                case FLIGHT:

                    break;
            }
        }
    }
}

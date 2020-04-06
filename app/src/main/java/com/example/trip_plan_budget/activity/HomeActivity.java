package com.example.trip_plan_budget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private UserModel user;
    private LinearLayout emptyView;
    private RecyclerView recyclerView;
    private boolean load = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.plans_recyclerview);
        emptyView = findViewById(R.id.empty_plans_view);
        findViewById(R.id.fab_add_plan)
                .setOnClickListener(view ->
                        startActivity(
                                new Intent(HomeActivity.this, TransportationActivity.class)));


        FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserModel.class);
                if (user != null) {
                    if (user.getPlans() == null) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        load = false;
                    } else if (user.getPlans().size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        load = false;
                    } else {
                        load = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}

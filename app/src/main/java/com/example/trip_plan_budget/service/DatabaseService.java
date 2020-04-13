package com.example.trip_plan_budget.service;

import androidx.annotation.NonNull;

import com.example.trip_plan_budget.interfaces.callback.AddPlanCallback;
import com.example.trip_plan_budget.model.flight.FlightModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseService {
    private static DatabaseService instance;
    private FirebaseDatabase database;

    private DatabaseService() {
        database = FirebaseDatabase.getInstance();
    }

    public static DatabaseService getInstance() {
        if (instance == null) instance = new DatabaseService();
        return instance;
    }

    public void addFlightPlan(FlightModel model, final AddPlanCallback callback) {
        String id = AuthService.getInstance().getID();
        database.getReference("users").child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long count = 1;
                        if (dataSnapshot.hasChild("plans")) {
                            count = dataSnapshot.child("plans").getChildrenCount() + 1;
                        }
                        dataSnapshot.child("plans").getRef().child(count + "").setValue(model)
                                .addOnCompleteListener(task -> {
                                    callback.onPlanAdded(task.isSuccessful());
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}

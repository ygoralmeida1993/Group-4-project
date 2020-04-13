package com.example.trip_plan_budget.activity.car;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.activity.places.ExplorerActivity;


public class CarBudgetPlannerActivity extends AppCompatActivity {
    //    Button b1, b2;
    boolean approach;
    EditText budgetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_budget);
//        b1 = this.findViewById(R.id.withBudget);
//        b2 = this.findViewById(R.id.withoutBudget);

        budgetText = findViewById(R.id.val_budget);
        findViewById(R.id.add_budget).setOnClickListener(view -> addBudget());
        findViewById(R.id.cancel_budget);
    }

    public void addBudget() {
        if (budgetText.getText().toString().isEmpty())
            budgetText.setError("Please enter a valid number");
        else {
            double budget = Double.parseDouble(budgetText.getText().toString());
            if (budget < 50) {
                budgetText.setError("Please enter a minimum budget of 50 Dollars");
            } else {
                Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
                intent.putExtra("budget", String.valueOf(budget));
                intent.putExtra("approach", 1);
                startActivity(intent);
            }
        }
    }

    public void showAddItemDialog(View view) {
        final EditText taskEditText = new EditText(this);
        taskEditText.setPadding(10, 0, 10, 0);
        taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add budget")
                .setMessage("")
                .setView(taskEditText)
                .setPositiveButton("Add", (dialog12, which) -> {
                    Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
                    String budget = taskEditText.getText().toString();
                    intent.putExtra("budget", budget);
                    intent.putExtra("approach", 1);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
    }

    public void withOutBudget(View view) {
        Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
        intent.putExtra("approach", 0);
        startActivity(intent);
    }
}
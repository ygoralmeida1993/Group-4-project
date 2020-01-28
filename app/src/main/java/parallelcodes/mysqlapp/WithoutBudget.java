package parallelcodes.mysqlapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class WithoutBudget extends AppCompatActivity {
    double approximateBudget=0;
    TextView budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_without_budget);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        approximateBudget = bundle.getDouble("approximateBudget");
        budget = (TextView) this.findViewById(R.id.text_budget);
        budget.setText(String.valueOf(Math.round(approximateBudget * 100.0) / 100.0));
    }

}

package parallelcodes.mysqlapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

//import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button b1, b2;
boolean approach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        b1 = (Button) this.findViewById(R.id.withBudget);
        b2 = (Button) this.findViewById(R.id.withoutBudget);
    }

    public void showAddItemDialog(View view) {
        final EditText taskEditText = new EditText(this);
        taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add budget")
                .setMessage("")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
                        String budget=taskEditText.getText().toString();
                        intent.putExtra("budget", budget);
                        intent.putExtra("approach",1);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void withOutBudget(View view) {
        Intent intent = new Intent(getApplicationContext(), ExplorerActivity.class);
        intent.putExtra("approach",0);
        startActivity(intent);
    }
}
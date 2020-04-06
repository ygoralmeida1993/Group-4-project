package com.example.trip_plan_budget.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trip_plan_budget.R;
import com.example.trip_plan_budget.activity.TransportationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText emailId;
    EditText password;
    EditText cpassword;

    Button buttonAdd;
    DatabaseReference databaseUsers;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailId = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.confirmPassword);
        buttonAdd = findViewById(R.id.SignUP);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance(); //need firebase authentication instance
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        //register user
        buttonAdd.setOnClickListener(view -> addUser());
    }

    public void addUser() {
        String email = emailId.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String cPassword = cpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailId.setError("Email id is required");
        } else if (TextUtils.isEmpty(Password)) {
            password.setError("Password is required");
        } else if (TextUtils.isEmpty(cPassword)) {
            cpassword.setError("confirm Password is required");
        } else if (cPassword.equals(password.getText().toString())) {
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(Password)) {
                firebaseAuth.createUserWithEmailAndPassword(email, Password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Log.d("failed", "error");
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), TransportationActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        });


            }
        } else {
            cpassword.setError("password does not match");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
package com.example.trip_plan_budget;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button Login;
    private EditText emailId;
    private EditText passwd;
    String Email;
    String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login=(Button) this.findViewById(R.id.LoginIn) ;
        emailId = (EditText)this.findViewById(R.id.emailid);
        passwd=(EditText)this.findViewById((R.id.password));
        firebaseAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Email=emailId.getText().toString();
                Password=passwd.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    emailId.setError("Email id is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    passwd.setError("Password is required");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, getString(R.string.loginfailed), Toast.LENGTH_LONG).show();
                                    }
                                 else {
                                    Intent intent = new Intent(LoginActivity.this, TrasportationAcitvity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
    public void renderSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}

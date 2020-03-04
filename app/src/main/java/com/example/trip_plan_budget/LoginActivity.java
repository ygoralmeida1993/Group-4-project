package com.example.trip_plan_budget;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CircleImageView circleImageView;
    private TextView txtName, txtEmail;
    private CallbackManager callbackManager;

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
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        loginButton = findViewById(R.id.login_button);
        txtName = findViewById(R.id.profile_name);
        txtEmail = findViewById(R.id.profile_email);
        circleImageView = findViewById(R.id.profile_pic);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    public void renderSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
            if (currentAccessToken==null)
            {
                txtName.setText("");
                txtEmail.setText("");
                circleImageView.setImageResource(0);
                Toast.makeText(LoginActivity.this, "User logged out", Toast.LENGTH_LONG);
            }
            else
                loadUserProfile(currentAccessToken);
        }
    };

    private void loadUserProfile (AccessToken newAccessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" +id+ "/picture?type=normal";

                    txtEmail.setText(email);
                    txtName.setText(first_name +" "+last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(LoginActivity.this).load(image_url).into(circleImageView);

                } catch (JSONException e){
                    e.printStackTrace();
                }


            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();

    }

}

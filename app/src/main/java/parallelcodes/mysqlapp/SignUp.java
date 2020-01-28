package parallelcodes.mysqlapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {
    private static final String url = "jdbc:mysql://10.24.26.178:3307/trip_plan";
    private static final String user = "root";
    private static final String pass = "";

    private EditText emailId;
    private EditText passwd;
    private EditText confirmPasswd;
    private EditText phnNo;
    private Button SignUp;
    String email;
    String password;
    String confirmPwd;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailId = (EditText) this.findViewById(R.id.emailid);
        phnNo = (EditText) this.findViewById(R.id.PhoneNumber);
        passwd = (EditText) this.findViewById((R.id.password));
        confirmPasswd = (EditText) this.findViewById((R.id.confirmPassword));

        SignUp = (Button) this.findViewById(R.id.SignUP);

        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = emailId.getText().toString();
                password = passwd.getText().toString();
                confirmPwd = confirmPasswd.getText().toString();
                phoneNumber = phnNo.getText().toString();


                if (!email.equals("") && !password.equals("") && !confirmPwd.equals("") && !phoneNumber.equals("")) {

                    if (password.equals(confirmPwd)) {
                    ConnectMySql connectMySql = new ConnectMySql();
                    connectMySql.execute(""); }
                    else{
                        Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_SHORT).show();
                    }
                } else {

                        emailId.setError("Email id is required");
                        passwd.setError("Password is required");
                        confirmPasswd.setError("Confirm Password is required");
                        phnNo.setError("Phone no. is required");

                }
            }
        });

    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";
        boolean added=true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);


                String result = "";
                Statement st = con.createStatement();

                String query = " insert into users (Email,Password,ConfirmPassword,PhoneNumber)"
                        + " values (?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString (1, email);
                preparedStmt.setString (2, password);
                preparedStmt.setString  (3,confirmPwd);

                preparedStmt.setString    (4, phoneNumber);

                // execute the preparedstatement
                added=  preparedStmt.execute();
                Log.d("check", String.valueOf(added));
                //ResultSet rs = st.executeQuery("INSERT INTO users(Email,Password,ConfirmPassword,PhoneNumber) VALUES(" + email + "," + password + "," + confirmPwd + "," + phoneNumber);
if(added){
    result="success";
}
               // ResultSetMetaData rsmd = rs.getMetaData();


                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("success")){
                Toast.makeText(getApplicationContext(),"User registration failed",Toast.LENGTH_SHORT).show();}
            else{
                Toast.makeText(getApplicationContext(),"User registered successfully",Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SignUp.this, Login.class);
                        startActivity(i);

            }
        }
    }
    }


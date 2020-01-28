package parallelcodes.mysqlapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    private static final String url = "jdbc:mysql://10.24.26.178/trip_plan";
    private static final String user = "root";
    private static final String pass = "";
    private EditText emailId;
    private EditText passwd;
    String Email;
    private Button Login;

    String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailId = (EditText)this.findViewById(R.id.emailid);
        passwd=(EditText)this.findViewById((R.id.password));
        Login=(Button) this.findViewById(R.id.LoginIn) ;

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Email=emailId.getText().toString();
                Password=passwd.getText().toString();

                if(!Email.equals("")&&!Password.equals(""))
                {
                    ConnectMySql connectMySql = new ConnectMySql();
                    connectMySql.execute("");
                }
                else{
                    emailId.setError("Email id is required");
                    passwd.setError("Password is required");
                }
            }
        });


    }
    public void renderSignUpScreen(View view) {
        Intent intent = new Intent(this, SignUp.class);

        startActivity(intent);
    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";
        boolean success=false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Login.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");

                String result = "";
                Statement st = con.createStatement();
                System.out.println("email password");

                ResultSet rs = st.executeQuery("select * from users where Email='"+Email+"' and Password='"+Password+"'");
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result += rs.getString("Id").toString() + "\n";
                }
                System.out.println("result");
                System.out.println(result);
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("")){
                Toast.makeText(getApplicationContext(),"Logged in failed",Toast.LENGTH_SHORT).show();}
            else{
                    Toast.makeText(getApplicationContext(),"Logged in successfully",Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Login.this, HomeActivity.class);
                        startActivity(i);


            }
            }
        }

}
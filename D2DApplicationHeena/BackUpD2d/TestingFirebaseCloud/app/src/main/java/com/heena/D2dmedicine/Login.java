package com.heena.D2dmedicine;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class Login extends AppCompatActivity {

    EditText ed_email, ed_password;

    TextView tv, fvpass;
    ProgressDialog pb;
    TextView tcondin;
    String email, password, user, name,city,state;
    ArrayList<Chemist> actorsList;
    CheckBox chk;

    Button btnLogin;

    AlertDialogManager alert = new AlertDialogManager();

    SessionManager1 session;
    String Expn =
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        session = new SessionManager1(getApplicationContext());

        ed_email = (EditText) findViewById(R.id.ed_lastName);


        ed_password = (EditText) findViewById(R.id.ed_password);

        chk = (CheckBox) findViewById(R.id.checkBox1);
        fvpass = (TextView) findViewById(R.id.fpass);
        pb = new ProgressDialog(Login.this);
        tv=(TextView)findViewById(R.id.tv_signup);
        tcondin=(TextView)findViewById(R.id.tv_tc);
        tcondin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this,Termspage.class);
                startActivity(it);
            }
        });

        actorsList = new ArrayList<Chemist>();

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
            TextView myMsg = new TextView(Login.this);
            myMsg.setText("Mobile Data is off");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(20);
            myMsg.setTextColor(Color.BLACK);
            builder.setCustomTitle(myMsg)
                    .setMessage("Turn on mobile data or use Wi-Fi to access data.")
                    .setPositiveButton("ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            }).setNegativeButton("Setting", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int which) {
                    startActivityForResult
                            (new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    dialog.cancel();
                }
            });
            builder.show();
        }

        btnLogin = (Button) findViewById(R.id.btn_Submit);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, RegisterOption.class);
                startActivity(it);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if ((chk).isChecked()) {
                    email = ed_email.getText().toString();
                    password = ed_password.getText().toString();

                    session.createLoginSession(email.replaceAll(" ",""), password.replaceAll(" ",""));
                    if (email.matches(Expn) && email.length() > 0) {
                        new Logmem().execute();
                    }


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.WHITE);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Please agree to Terms and Conditions Policy.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.show();
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        fvpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this, ForgetPassword.class);
                startActivity(it);
            }
        });
    }

    private class Logmem extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMessage("Please wait while Loading...");
            pb.show();
            email = ed_email.getText().toString().trim();
            password = ed_password.getText().toString().trim();
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=2&email="+email.replaceAll(" ","")+"&password="+password.replaceAll(" ","")+"";
            String SetServerString = "";
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = httpClient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + SetServerString);
            return SetServerString;
        }

        protected void onPostExecute(String result) {
            pb.cancel();
            try {
                pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {

                    String message2 = jsonResult.getString("result");
                    if (message2.equals("1")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        TextView myMsg = new TextView(Login.this);
                        myMsg.setText("Congratulations!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);
                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("You have logged in successfully.");
                        builder.setPositiveButton("Continue.",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent it = new Intent(Login.this, Mem.class);
                                        startActivity(it);
                                        }
                                });
                        builder.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        TextView myMsg = new TextView(Login.this);
                        myMsg.setText("Congratulations!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);
                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("You have logged in successfully.");
                        builder.setPositiveButton("Continue.",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent it = new Intent(Login.this,UploadMedicine.class);
                                        Bundle b = new Bundle();
                                        b.putString("email", email);
                                        it.putExtras(b);
                                        startActivity(it);
                                    }
                                });
                        builder.show();


                    }
                }
                else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Email/Password is invalid.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

package com.administrator.renudadlani;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Register extends AppCompatActivity {
    EditText fname1, lname1, email1, pass1, confirm1, age1, countr1, username1;
    String fname, lstname, emai, pass,username, country,  confirm,token;
    Button btnsub;
    ImageView iback;
    ImageButton pass_im,con_im;
    String Search="@";
    int count=0;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {

                    token = intent.getStringExtra("token");

                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (ConnectionResult.SUCCESS != resultCode) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }
        } else {

            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
            TextView myMsg = new TextView(Register.this);
            myMsg.setText("Mobile Data is off");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(20);
            myMsg.setTextColor(Color.BLACK);
            builder.setCustomTitle(myMsg);
            builder.setMessage("Turn on mobile data or use Wi-Fi to access data.");
            builder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.cancel();
                        }
                    });
            builder.setNegativeButton("Setting", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int which) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    dialog.cancel();
                }
            });
            builder.show();
        }

        fname1 = (EditText) findViewById(R.id.ed_lastName);
        //fname1.setTypeface(font);
        lname1 = (EditText) findViewById(R.id.ed_firstName);
        // lname1.setTypeface(font);
        email1 = (EditText) findViewById(R.id.ed_email);
        //email1.setTypeface(font);
        pass1 = (EditText) findViewById(R.id.ed_password);
        //pass1.setTypeface(font);
        confirm1 = (EditText) findViewById(R.id.ed_confirmpass);
        // confirm1.setTypeface(font);
        countr1 = (EditText) findViewById(R.id.ed_country);
        //countr1.setTypeface(font);
        username1 = (EditText) findViewById(R.id.ed_username);
        // username1.setTypeface(font);
        btnsub = (Button) findViewById(R.id.btn_Submit);
        pass_im = (ImageButton) findViewById(R.id.im_pass);
        con_im = (ImageButton) findViewById(R.id.im_conpass);

        pass_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count == 0) {
                    pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    count++;
                } else {
                    pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    count = 0;
                }

            }
        });

        con_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    confirm1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    count++;
                } else {
                    confirm1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    count = 0;
                }

            }
        });
        // btnsub.setTypeface(font);
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = fname1.getText().toString();
                lstname = lname1.getText().toString();
                emai = email1.getText().toString();
                pass = pass1.getText().toString();
                confirm = confirm1.getText().toString();
                username = username1.getText().toString();
                country = countr1.getText().toString();

                if (fname.matches("") || lstname.matches("") || emai.matches("") || pass.matches("")
                        || confirm.matches("") || username.matches("") ||
                        country.matches("")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("All fields are mandatory.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();

                } else {
                    if (emai.toLowerCase().indexOf(Search.toLowerCase()) != -1) {
                        if (pass.equals(confirm)) {
                            new regi().execute();
                        } else {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                            TextView myMsg = new TextView(Register.this);
                            myMsg.setText("Warning!");
                            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                            myMsg.setTextSize(20);

                            myMsg.setTextColor(Color.BLACK);

                            builder.setCustomTitle(myMsg);
                            builder.setMessage("Password do not match.");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.cancel();
                                            pass1.setText("");
                                            confirm1.setText("");


                                        }
                                    });
                            builder.show();
                        }

                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                        TextView myMsg = new TextView(Register.this);
                        myMsg.setText("Warning!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);

                        myMsg.setTextColor(Color.BLACK);

                        builder.setCustomTitle(myMsg);
                        builder.setMessage("Email is not valid.");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                        email1.setText("");

                                    }
                                });
                        builder.show();
                    }
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    private class pushnotify extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            emai = email1.getText().toString().trim();
            emai=emai.replaceAll(" ","").trim();
            country = token;
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url= "http://renudadlani.com/mobile_app/gg.php?case_id=1&userid="+emai+
                    "&regid="+country+"";
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
            try {
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1"))
                {
                }

                else if (message.equals("0")){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Not Registered successfully");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(getApplicationContext(), Register.class);
                                    startActivity(it);
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
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }

    private class regi extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            fname = fname1.getText().toString().trim();
            fname=fname.replaceAll(" ","").trim();
            lstname = lname1.getText().toString().trim();
            lstname=lstname.replaceAll(" ","").trim();
            emai = email1.getText().toString().trim();
            emai=emai.replaceAll(" ","").trim();
            pass = pass1.getText().toString().trim();
            pass=pass.replaceAll(" ","").trim();
            confirm = confirm1.getText().toString().trim();
            confirm=confirm.replaceAll(" ","").trim();
            username = username1.getText().toString().trim();
            username=username.replaceAll(" ","").trim();
            country = token;
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url= "http://renudadlani.com/mobile_app/insertdata.php?case_id=1" +
                    "&fname="+fname+"&lname="+lstname+"&email="+emai+"" +
                    "&password="+pass+"&username="+username+"&token="+country+"";
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
            try {
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1"))
                {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have registered successfully.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(getApplicationContext(), Login.class);

                                    startActivity(it);
                                }
                            });
                    builder.show();

                }
                else if (message.equals("2")){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Registration failed.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(getApplicationContext(), Register.class);
                                    startActivity(it);
                                }
                            });

                    builder.show();
                }
                else if (message.equals("0")){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage(" Email Already Exist");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(getApplicationContext(), Register.class);
                                    startActivity(it);
                                }
                            });

                    builder.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}


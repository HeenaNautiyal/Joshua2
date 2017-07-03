package com.administrator.bizhawkz.d2d;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/28/2016.
 */
public class Login extends AppCompatActivity {
    EditText ed_email, ed_password;
    ImageView btnLog;
    TextView tv, fvpass, ChemistLog;
    ProgressDialog pb;
    String email, password, user, name, message1,number,house,colony,city,state;
    ArrayList<Chemist> actorsList;

    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(Login.this, Mem.class);
                startActivity(intent);
            } else {
        }


        user = settings.getString("user", email);
        fvpass = (TextView) findViewById(R.id.fpass);
        pb = new ProgressDialog(Login.this);
        ChemistLog = (TextView) findViewById(R.id.tv_cust);

        actorsList = new ArrayList<Chemist>();

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        tv = (TextView) findViewById(R.id.tv_signup);
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this, RegisterOption.class);
                startActivity(it);
            }
        });

        ChemistLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this, ChemLog.class);
                startActivity(it);
            }
        });
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(com.administrator.d2d.Login.this);
            TextView myMsg = new TextView(com.administrator.d2d.Login.this);
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
        ed_email = (EditText) findViewById(R.id.ed_lastName);
        ed_password = (EditText) findViewById(R.id.ed_password);
        btnLog = (ImageView) findViewById(R.id.btn_Submit);


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = ed_email.getText().toString();
                password = ed_password.getText().toString();
                if (email.matches("") || password.matches("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg).setMessage("All fields are mandatory.")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                        }
                                    })
                            .show();
                    ed_email.setText("");
                    ed_password.setText("");
                } else {
                    new Logmem().execute("http://seajob.net/medicalapp/insertdata.php?caseid=2&email="+email.replaceAll(" ","")+"&password="+password.replaceAll(" ","")+"");
                }
            }
        });

        fvpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this, ForgetPassword.class);
                startActivity(it);
            }
        });
    }

    /*  Asynchronus Task for Login*/
    private class Logmem extends AsyncTask<String, Void, Boolean> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMessage("Please wait while Loading...");
            pb.show();
            email = ed_email.getText().toString().trim();
            password = ed_password.getText().toString().trim();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);
                    String message = jsono.getString("udata");
                    if (message.equals("1")) {
                        message1 = "Success";
                       JSONArray jarray = jsono.getJSONArray("result");

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            Chemist actor = new Chemist();

                            actor.setFname(object.getString("fname"));
                            actor.setLname(object.getString("lname"));
                            actor.setMail(object.getString("email"));
                            actor.setState(object.getString("state"));
                            actor.setMobile(object.getString("mobilenumber"));
                            actor.setPincode(object.getString("pincode"));
                            actor.setHouse(object.getString("houseno"));
                            actor.setColony(object.getString("colony"));

                            name = actor.getFname();
                            actorsList.add(actor);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("logged", "logged");
                            editor.putString("username", name);
                            editor.putString("email",email);
                            editor.putString("mobile",number);
                            editor.putString("house",house);
                            editor.putString("colony",colony);
                            editor.putString("city",city);
                            editor.putString("state",state);
                            editor.commit();
                        }

                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            pb.cancel();
            if (result == false) {
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            } else {
                Intent it = new Intent(com.administrator.d2d.Login.this, Mem.class);

                startActivity(it);
            }

        }
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("logged", "logged");
        editor.putString("username", name);
        editor.commit();
    }

    protected  void onResume(){
        super.onResume();
    }
}


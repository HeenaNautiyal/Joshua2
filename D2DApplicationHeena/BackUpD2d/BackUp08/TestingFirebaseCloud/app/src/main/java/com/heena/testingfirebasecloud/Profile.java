package com.heena.testingfirebasecloud;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

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
import java.util.HashMap;

public class Profile extends AppCompatActivity {

    TextView email1,num,address,city1,pincode1,name;
    ImageView bback;
    String mail,name1,number1,address1,city,state1,pincode,mail4;
    SharedPreferences settings;
    ArrayList<Chemist> actorsList;

    ActorAdapter adapter;
    SessionManager1 session;
    public static final String PREFS_NAME = "LoginPrefs";
    ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        settings = getSharedPreferences(PREFS_NAME, 0);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        pb = new ProgressDialog(Profile.this);
        session = new SessionManager1(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        mail4 = user.get(SessionManager1.KEY_EMAIL);
        name = (TextView) findViewById(R.id.textView3);
        email1 = (TextView) findViewById(R.id.mail);
        num = (TextView) findViewById(R.id.number);
        address = (TextView) findViewById(R.id.address);
        city1 = (TextView) findViewById(R.id.city);
        pincode1 = (TextView) findViewById(R.id.pincode);
        bback=(ImageView)findViewById(R.id.imgback);

        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Profile.this,Main2Activity.class);
                startActivity(it);
            }
        });
        new Logmem().execute("http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=19&email=" + mail4 + "");
    }

    private class Logmem extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Profile.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("result");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        name1 = object.getString("first_name");
                        number1 = object.getString("mobileno");
                        address1 = object.getString("address");
                        city = object.getString("city");
                        state1 = object.getString("state");
                        pincode = object.getString("pincode");
                    }
                    return true;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result)  {
            dialog.cancel();
            name.setText(name1);
            email1.setText(mail4);
            num.setText(number1);
            address.setText(address1);
            city1.setText(city);
            pincode1.setText(pincode);
            if(result == false){
                final AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                TextView myMsg = new TextView(Profile.this);
                myMsg.setText("Alert!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Please login to proceed further.");
                builder.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                session.logoutUser();
                            }
                        });
                builder.show();
            }
        }
    }
}

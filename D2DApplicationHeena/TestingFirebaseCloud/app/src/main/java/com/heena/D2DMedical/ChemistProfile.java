package com.heena.D2DMedical;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.HashMap;

public class ChemistProfile extends AppCompatActivity {
    Button btn_continue;
    TextView number,license,state,pincode1,name,address,email,expiery;

    ImageView btnback;
    String aaa,expierydate,mail,name1,mail1,number1,address1,city,state1,pincode,licensenum,mail4;
    private CoordinatorLayout coordinatorLayout;
    SessionManager1 session;
    ProgressDialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        number=(TextView)findViewById(R.id.number);
        license=(TextView)findViewById(R.id.license);
        expiery=(TextView)findViewById(R.id.expiry);
        address=(TextView)findViewById(R.id.Address);
        state=(TextView)findViewById(R.id.state);
        pb = new ProgressDialog(ChemistProfile.this);
       // pincode1=(TextView)findViewById(R.id.pin);
        btnback=(ImageView)findViewById(R.id.imgback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChemistProfile.this,UploadMedicine.class);
                startActivity(it);
            }
        });

        session = new SessionManager1(getApplicationContext());

/*        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();*/

        HashMap<String, String> user = session.getUserDetails();
        mail1 = user.get(SessionManager1.KEY_EMAIL);
        new Logmem().execute("http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=21&email="+mail1.replaceAll(" ","")+"");

    }



    private class Logmem extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChemistProfile.this);
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
                        mail4=object.getString("email_id");
                        number1 = object.getString("mobile_number");
                        address1 = object.getString("address");
                        state1=object.getString("state");
                        licensenum=object.getString("license_number");
                        expierydate=object.getString("exp_date");

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

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            name.setText(name1);
            email.setText(mail4);
            number.setText(number1);
            address.setText(address1);
            state.setText(state1);
            license.setText(licensenum);
            expiery.setText(expierydate);
            if(result == false)
                Toast.makeText(getApplicationContext(),"Unable to find profile",Toast.LENGTH_LONG).show();
        }
    }
}

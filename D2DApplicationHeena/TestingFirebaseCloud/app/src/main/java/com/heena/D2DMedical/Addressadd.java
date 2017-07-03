package com.heena.D2DMedical;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Addressadd extends AppCompatActivity {
ImageView iv;
    Button btn;

    TextView sk,email1,num,address,city1,pincode1,name,state;
    SessionManager1 session;
    Snackbar snackbar;
    EditText address2,city2,state2,pincode2,num1,edmail2;
    String aaa,mail2,mail,name1,number1,address1,city,state1,pincode,stad,stcty,ststat,stpin,stnum;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressadd);
       /* getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();
*/
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        address=(TextView)findViewById(R.id.adress);
        city1=(TextView)findViewById(R.id.city);
        state=(TextView)findViewById(R.id.state);
        pincode1=(TextView)findViewById(R.id.pin);


        address2=(EditText)findViewById(R.id.ed_lastName);
        city2=(EditText)findViewById(R.id.ed_city);
        state2=(EditText)findViewById(R.id.ed_state);
        pincode2=(EditText)findViewById(R.id.ed_pincode);
        num1=(EditText)findViewById(R.id.ed_numn);
        edmail2=(EditText)findViewById(R.id.ed_mail2);

        btn=(Button)findViewById(R.id.btn_continue);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stad = address2.getText().toString();
                stcty = city2.getText().toString();
                ststat = state2.getText().toString();
                stpin = pincode2.getText().toString();
                stnum=num1.getText().toString();
                new Registration().execute();
            }
        });



        session = new SessionManager1(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        mail = user.get(SessionManager1.KEY_EMAIL);

        new Logmem().execute("http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=19&email="+mail+"");

        iv=(ImageView)findViewById(R.id.imgback);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(Addressadd.this,UploadPresc.class);
                Bundle b = new Bundle();
                b.putString("name", aaa);
                it.putExtras(b);
                startActivity(it);
            }
        });
    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private class Logmem extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Addressadd.this);
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
                        state1=object.getString("state");
                        pincode=object.getString("pincode");
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
            address.setText(address1);
            city1.setText(city);
            state.setText(state1);
            pincode1.setText(pincode);

            if(result == false)
               Toast.makeText(getApplicationContext(),"Unable to fetch data from server",Toast.LENGTH_LONG).show();
                /* snackbar = Snackbar
                        .make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        }
    }
        private class Registration extends AsyncTask<String, String, String> {
            ProgressDialog dialog;
            protected void onPreExecute() {
                dialog = new ProgressDialog(Addressadd.this);
                dialog.setMessage("Loading, please wait");
                dialog.setTitle("Connecting server");
                dialog.show();
                dialog.setCancelable(false);

                stad = address2.getText().toString();
                stcty = city2.getText().toString();
                ststat = state2.getText().toString();
                stpin = pincode2.getText().toString();
                stnum=num1.getText().toString();
                mail2=edmail2.getText().toString();


            }

            @Override
            protected String doInBackground(String... strings) {
                HttpClient httpClient = new DefaultHttpClient();
                String url="http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=18&email="+mail+"" +
                        "&mobileno="+stnum+"&pincode="+stpin+"&address="+stad.replaceAll(" ","%20")+"" +
                        "&city="+stcty.replaceAll(" ","%20")+"&state="+ststat.replaceAll(" ","%20")+"&newemail="+mail2.replaceAll(" ","");
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
                    dialog.dismiss();
                    JSONObject jsonResult = new JSONObject(result);
                    String message = jsonResult.getString("udata");
                    Log.d("Response: ", "> " + message);
                    if (message.equals("1")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Addressadd.this);
                        TextView myMsg = new TextView(Addressadd.this);
                        myMsg.setText("Congratulations!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);
                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("Your alternate Delivery address has been changed");
                        builder.setPositiveButton("Continue.",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent it= new Intent(Addressadd.this,UploadPresc.class);
                                        Bundle b = new Bundle();
                                        b.putString("name", aaa);
                                        it.putExtras(b);
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

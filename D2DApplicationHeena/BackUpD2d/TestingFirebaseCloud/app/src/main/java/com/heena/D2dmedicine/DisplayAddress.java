package com.heena.D2dmedicine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
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

public class DisplayAddress extends AppCompatActivity {
    SessionManager1 session;
    ArrayList<Chemist> actorsList;
    ActorAdapter adapter;
    ImageView btnback;
    String mail1;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    String TAG = "Display Address",mail,st1,st2,st3,st4,st5,st6,st7,st8;
    Button btn,OK1;
    CheckBox chsave,chkrescent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_address);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        session = new SessionManager1(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        mail1 = user.get(SessionManager1.KEY_EMAIL);

        actorsList = new ArrayList<Chemist>();
        new JSONAsyncTask().execute("http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=19&email="+mail1+"");
        ListView listview = (ListView) findViewById(R.id.list);

        btnback = (ImageView) findViewById(R.id.imgback);
        btn=(Button)findViewById(R.id.btn_continue);
         tv1=(TextView)findViewById(R.id.adress);
        tv2=(TextView)findViewById(R.id.city);
        tv3=(TextView)findViewById(R.id.state);
        tv4=(TextView)findViewById(R.id.pin);
        tv5=(TextView)findViewById(R.id.adress2);
        tv6=(TextView)findViewById(R.id.city2);
        tv7=(TextView)findViewById(R.id.state2);
        tv8=(TextView)findViewById(R.id.pin2);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent It = new Intent(DisplayAddress.this, Mem.class);
                startActivity(It);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog(R.style.DialogTheme, "Left - Right Animation!");
            }
        });

    }

    private void buildDialog(int dialogTheme, String s) {
        LayoutInflater layoutInflater = LayoutInflater.from(DisplayAddress.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog3, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DisplayAddress.this);
        alertDialogBuilder.setView(promptView);
        AlertDialog alert = alertDialogBuilder.create();

        chsave= (CheckBox) promptView.findViewById(R.id.checkBox1);
        chkrescent=(CheckBox)promptView.findViewById(R.id.checkBox2);
        
        OK1=(Button)promptView.findViewById(R.id.btn_ok);
        OK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chsave.isChecked())
                {
                    chkrescent.setChecked(false);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DisplayAddress.this);
                    TextView myMsg = new TextView(DisplayAddress.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("your order has been set to the chemist.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(DisplayAddress.this, Main2Activity.class);
                                    startActivity(it);
                                }
                            });
                    builder.show();
                    }
                else{
                    chsave.setChecked(false);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DisplayAddress.this);
                    TextView myMsg = new TextView(DisplayAddress.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("your order has been set to the chemist.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(DisplayAddress.this, Main2Activity.class);
                                    startActivity(it);
                                }
                            });
                    builder.show();
                }
            }
        });


        alert.getWindow().getAttributes().windowAnimations = dialogTheme;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;
        wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position

        alert.show();
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DisplayAddress.this);
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

                        st1 = object.getString("first_name");
                        st2 = object.getString("mobileno");
                        st3 = object.getString("address");
                        st4 = object.getString("city");
                        st5=object.getString("email2");
                        st6=object.getString("mobileno2");
                        st7=object.getString("address2");
                        st8=object.getString("address2");

                        /*state1=object.getString("state");
                        pincode=object.getString("pincode");*/
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
            tv1.setText(st1);
            tv2.setText(st2);
            tv3.setText(st3);
            tv4.setText(st4);
            tv5.setText(st5);
            tv6.setText(st6);
            tv7.setText(st7);
            tv8.setText(st8);

            if (result == false) {
                Toast.makeText(getApplicationContext(),"Unable to fetch data from server",Toast.LENGTH_LONG).show();
            }
        }

        public void onBackPressed() {
            moveTaskToBack(true);
        }
    }


}

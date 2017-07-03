package com.heena.testingfirebasecloud;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.heena.testingfirebasecloud.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ordernow extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String abc,item1,quan,med,item;
    TextView tvmed;
    EditText edquan;
    ProgressDialog pb;
    TextView ed;
    Button  OK1,btn,chk1;
    String stfinal,mail1,chemmail;
    SessionManager1 session;
    private int count=0;
    private TextView mCounter;
    String medid1;
    ImageView addcart,back;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordernow);
        Bundle b = getIntent().getExtras();
        pb = new ProgressDialog(Ordernow.this);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();
        session = new SessionManager1(getApplicationContext());

        final Controller ct = (Controller) getApplicationContext();
        Bundle c = getIntent().getExtras();
        abc = (String) c.getCharSequence("medicine");
        medid1=(String)c.getCharSequence("medid");
        tvmed = (TextView) findViewById(R.id.medicine);
        tvmed.setText(abc);

        HashMap<String, String> user = session.getUserDetails();

        mail1 = user.get(SessionManager1.KEY_EMAIL);

        chemmail = user.get(SessionManager1.KEY_CHEMEMAIL);

        String status=  chemmail = user.get(SessionManager1.KEY_CHEMEMAIL);

        edquan = (EditText) findViewById(R.id.edquantity);
        back=(ImageView) findViewById(R.id.imgback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent it = new Intent(Ordernow.this,Main2Activity.class);
                startActivity(it);
            }
        });
        final int index = 0;


        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        btn = (Button) findViewById(R.id.btn_continue);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item1=item;
                med=abc;
                quan=edquan.getText().toString();
                if(quan.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please add the quantity for medicine before proceeding",Toast.LENGTH_LONG).show();

                }
                else {
                    stfinal = "Medicine :" + med ;
                    buildDialog(R.style.DialogTheme, "Left - Right Animation!");
                }
            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("none");
        categories.add("Weekly");
        categories.add("15days");
        categories.add("Monthly");
        categories.add("2Month");
        categories.add("3Month");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
      }

    private void startAlert() {
        if (item1 == "daily") {
            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (1 * 6000), pendingIntent);
            }
        }

    private void buildDialog(int animationSource, String s) {
        LayoutInflater layoutInflater = LayoutInflater.from(Ordernow.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog2, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ordernow.this);
        alertDialogBuilder.setView(promptView);

        //final AlertDialog show = alertDialogBuilder.show();
        final AlertDialog alert = alertDialogBuilder.create();

        OK1 = (Button) promptView.findViewById(R.id.btn_ok);
        ed = (TextView) promptView.findViewById(R.id.edittext);
        ed.setText(stfinal);
        OK1 = (Button) promptView.findViewById(R.id.btn_ok);
        OK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            /*   new Order();*/

            }
        });
        chk1 = (Button) promptView.findViewById(R.id.btn_chk);
        chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("medicineid:",medid1);
                alert.dismiss();
               /* Intent it = new Intent(Ordernow.this, OrderList.class);
                startActivity(it);*/
                new addorder().execute();
            }
        });

        alert.getWindow().getAttributes().windowAnimations = animationSource;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM;

        alert.show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private class addorder extends AsyncTask<String ,String,String> {
        @Override
        protected void onPreExecute() {
            pb.setMessage("Please wait while Loading...");
            pb.show();
          }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            String url ="http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=28" +
                    "&medicine_id="+medid1.replaceAll(" ","")+"&user_eamil="+mail1.replaceAll(" ","")+"" +
                    "&chemist_email="+chemmail.replaceAll(" ","")+"&order_qty="+quan+"";
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
                if (message.equals("1")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Ordernow.this);
                    TextView myMsg = new TextView(Ordernow.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Your Order has been send to the chemist.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                   dialog.dismiss();
                                    Intent it= new Intent(Ordernow.this,Main2Activity.class);
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

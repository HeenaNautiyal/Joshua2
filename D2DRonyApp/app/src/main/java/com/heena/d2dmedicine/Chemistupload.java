package com.heena.d2dmedicine;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

public class Chemistupload extends AppCompatActivity {
   EditText catid, medname, manuby, comp, addedby,  stock;
    TextView userip;
    String catid1;
    String name,currentdate;
    String manu;
    String comp1;
    String add;
    EditText date;
    String ip;
    String stock1;
    String IPaddress;
    String message1;
    ArrayList<Chemist> actorsList;
    ProgressDialog pb;
    ActorAdapter2 adapter;
    String TAG = "MainActivity";
    Button btn;
    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistupload);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();
        // pb = new ProgressDialog(Register.this);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        catid = (EditText) findViewById(R.id.ed_catid);
        medname = (EditText) findViewById(R.id.ed_medname);
        manuby = (EditText) findViewById(R.id.ed_manu);
        comp = (EditText) findViewById(R.id.ed_comp);
        addedby = (EditText) findViewById(R.id.ed_chemist);
        stock = (EditText) findViewById(R.id.ed_stock);
        userip = (TextView) findViewById(R.id.tv_ip);
        btn = (Button) findViewById(R.id.btn_submit);

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    fromDatePickerDialog.show();
                }else {                                 }
            }
        });

        NetwordDetect();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Upload().execute();
            }
        });
    }

    private void findViewsById() {
        date = (EditText) findViewById(R.id.ed_date);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();
    }


    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void NetwordDetect() {

        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

                if (netInfo.isConnected())

                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

                if (netInfo.isConnected())

                    MOBILE = true;
        }

        if (WIFI == true)

        {
            IPaddress = GetDeviceipWiFiData();
            userip.setText(IPaddress);
        }

        if (MOBILE == true) {
            IPaddress = GetDeviceipMobileData();
            userip.setText(IPaddress);
        }

    }

    public String GetDeviceipMobileData() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public String GetDeviceipWiFiData() {

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

        @SuppressWarnings("deprecation")

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }

    private class Upload extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            catid1 = catid.getText().toString();
            name = medname.getText().toString();
            manu = manuby.getText().toString();
            comp1 = comp.getText().toString();
            add = addedby.getText().toString();
            currentdate = date.getText().toString();
            stock1 = stock.getText().toString();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url="http://seajob.net/medicalapp/insertdata.php?caseid=12&cat_id="+catid1.replaceAll(" ","")+"" +
                    "&med_name="+name.replaceAll(" ","%20")+"&manu_by="+manu.replaceAll(" ","%20")+"" +
                    "&comps="+comp1.replaceAll(" ","%20")+"&added_by="+add.replaceAll(" ","%20")+"" +
                    "&today_date=12/15/2016&user_ip=" + IPaddress + "&stock=1";
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
                //pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Chemistupload.this);
                    TextView myMsg = new TextView(Chemistupload.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have successfully added Medicine.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    catid.setText("");
                                    medname.setText("");
                                    manuby.setText("");
                                    comp.setText("");
                                    addedby.setText("");
                                    date.setText("");
                                    stock.setText("");
                                    Intent it = new Intent(Chemistupload.this, SearchChemist.class);
                                    startActivity(it);

                                }
                            });
                    builder.show();
                } else {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Chemistupload.this);
                    TextView myMsg = new TextView(Chemistupload.this);
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
}




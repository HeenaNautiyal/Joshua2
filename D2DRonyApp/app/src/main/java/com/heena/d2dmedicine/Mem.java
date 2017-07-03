package com.heena.d2dmedicine;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Mem extends AppCompatActivity {
    Button btnGPSShowLocation;
    Button btnPincode;
    TextView tvAddress;
    String a,lat,lon,message2;
    EditText ed;
    ImageView btnback;
    double latitude,longitude;
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    AppLocationService appLocationService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem);
        btnback=(ImageView)findViewById(R.id.backbtn);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getString("logged", "").toString().equals("logged")) {
            String value = settings.getString("licenseno",null);
            if (value == null) {
                Toast.makeText(getApplicationContext(),"You have to Login First.",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),value.toString(),Toast.LENGTH_LONG).show();
               // startActivity(intent);
            }
        }

        //tvAddress = (TextView) findViewById(R.id.tvAddress);
        appLocationService = new AppLocationService(Mem.this);

        btnGPSShowLocation = (Button) findViewById(R.id.btnGPSShowLocation);
        btnGPSShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Location gpsLocation = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);
                if (gpsLocation != null) {
                    latitude = gpsLocation.getLatitude();
                    longitude = gpsLocation.getLongitude();
                    String result = "Latitude: " + gpsLocation.getLatitude() +
                            " Longitude: " + gpsLocation.getLongitude();
                    new  Loggps().execute();
                } else {
                    showSettingsAlert();
                }
                lat= String.valueOf(latitude);
                lon=String.valueOf(longitude);
            }

        });

        btnPincode = (Button) findViewById(R.id.btnpincode);
        btnPincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                LayoutInflater layoutInflater = LayoutInflater.from(Mem.this);
                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mem.this);
                alertDialogBuilder.setView(promptView);

                ed=(EditText)promptView.findViewById(R.id.edittext);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                a=ed.getText().toString().trim();
                                Intent It =new Intent(Mem.this,Nearchemeist.class);
                                Bundle b = new Bundle();
                                b.putString("pincode",a);
                                It.putExtras(b);
                                startActivity(It);
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Mem.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Mem.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class Loggps extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url="http://seajob.net/medicalapp/insertdata.php?caseid=11&latitude="+lat+"&longitude="+lon+"";
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
                    message2 = jsonResult.getString("result");
                    Intent It =new Intent(Mem.this,Nearchemeist.class);
                    Bundle b = new Bundle();
                    b.putString("pincode",message2);
                    It.putExtras(b);
                    startActivity(It);
                  }
                else {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Mem.this);
                    TextView myMsg = new TextView(Mem.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Unable to get Location.");
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

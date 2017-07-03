package com.heena.testingfirebasecloud;

import android.app.Dialog;
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
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Mem extends AppCompatActivity {
    Button btnGPSShowLocation;
    Button btnPincode,OK1,Decline1;
    TextView tvAddress;
    String a, lat, lon, message2;
    EditText ed;
    ImageView close1;
    double latitude, longitude;

    SessionManager1 session;
    //SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    AppLocationService appLocationService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        session = new SessionManager1(getApplicationContext());
        session.checkLogin();

        View btnShowDialog = findViewById(R.id.btnpincode);

        btnShowDialog.setOnClickListener(onClickListener(1));
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
                    new Loggps().execute();
                } else {
                    showSettingsAlert();
                }
                lat = String.valueOf(latitude);
                lon = String.valueOf(longitude);
            }

        });
    }

    private View.OnClickListener onClickListener(final int style) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (style == 1) {
                    buildDialog(R.style.DialogTheme, "Left - Right Animation!");
                }
            }
        };
    }
    private void buildDialog(int animationSource, String type) {
        LayoutInflater layoutInflater = LayoutInflater.from(Mem.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mem.this);
        alertDialogBuilder.setView(promptView);
        AlertDialog alert = alertDialogBuilder.create();

        ed = (EditText) promptView.findViewById(R.id.edittext);
        OK1=(Button)promptView.findViewById(R.id.btn_ok);
        OK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = ed.getText().toString().trim();
                Intent It = new Intent(Mem.this, Nearchemeist.class);
                Bundle b = new Bundle();
                b.putString("pincode", a);
                It.putExtras(b);
                startActivity(It);
            }
        });


        alert.getWindow().getAttributes().windowAnimations = animationSource;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;
        wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position

        alert.show();

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
            String url = "http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=11&latitude=" + lat + "&longitude=" + lon + "";
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
                    Intent It = new Intent(Mem.this, Nearchemeist.class);
                    Bundle b = new Bundle();
                    b.putString("pincode", message2);
                    It.putExtras(b);
                    startActivity(It);

                } else {
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

/*
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        session = new SessionManager1(getApplicationContext());

        //settings = getSharedPreferences(PREFS_NAME, 0);
        View btnShowDialog = findViewById(R.id.btnpincode);
        btnShowDialog.setOnClickListener(onClickListener(1));
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        String name = user.get(SessionManager1.KEY_EMAIL);

        // email
        String email = user.get(SessionManager1.KEY_PASSWORD);

        Toast.makeText(getApplicationContext(),name+email,Toast.LENGTH_LONG).show();


    *//*    if (settings.getString("logged", "").toString().equals("logged")) {
            String value = settings.getString("licenseno", null);
            if (value == null) {
                //Toast.makeText(getApplicationContext(), "You have to Login First.", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getApplicationContext(), value.toString(), Toast.LENGTH_LONG).show();
                // startActivity(intent);
            }
        }*//*

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
                    new Loggps().execute();
                } else {
                    showSettingsAlert();
                }
                lat = String.valueOf(latitude);
                lon = String.valueOf(longitude);
            }

        });



        //  btnPincode = (Button) findViewById(R.id.btnpincode);

    }
        private View.OnClickListener onClickListener(final int style) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (style == 1) {
                        buildDialog(R.style.DialogTheme, "Left - Right Animation!");
                    }
                }
            };
        }
    private void buildDialog(int animationSource, String type) {

        LayoutInflater layoutInflater = LayoutInflater.from(Mem.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mem.this);
        alertDialogBuilder.setView(promptView);


        ed = (EditText) promptView.findViewById(R.id.edittext);
        OK1=(Button)promptView.findViewById(R.id.btn_ok);
        OK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = ed.getText().toString().trim();
                Intent It = new Intent(Mem.this, Nearchemeist.class);
                Bundle b = new Bundle();
                b.putString("pincode", a);
                It.putExtras(b);
                startActivity(It);
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().getAttributes().windowAnimations = animationSource;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;
       *//* wmlp.x = 100;   //x position
        wmlp.y = 100;  *//* //y position

        alert.show();
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
            String url = "http://seajob.net/medicalapp/insertdata.php?caseid=11&latitude=" + lat + "&longitude=" + lon + "";
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
Intent It = new Intent(Mem.this, Nearchemeist.class);
                    Bundle b = new Bundle();
                    b.putString("pincode", message2);
                    It.putExtras(b);
                    startActivity(It);

                } else {
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

    }*/
}

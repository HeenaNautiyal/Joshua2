package com.administrator.renudadlani;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Login extends AppCompatActivity {

    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    String email;

    String fbname, token, userfb, userfb1;
    EditText username, password, fgusername, fgpassword, fgConfirmpass, input;
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb = "F";
    String user, pass, fu, fu1, value, token1;
    Button btn, ok, can;
    TextView fp;
    final Context context = this;
    SharedPreferences settings, pwd;
    ImageView iback;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_login);
        getFbKeyHash();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                displayMessage(currentProfile);
            }
        };

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {

                    token = intent.getStringExtra("token");

                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {

                } else {

                }
            }
        };
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (ConnectionResult.SUCCESS != resultCode) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
            }
        } else {
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }
        getFbKeyHash();
        getLoginDetails(loginButton);
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                displayMessage(currentProfile);
            }
        };

        fgusername = (EditText) findViewById(R.id.editid);

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // Check for network connections
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts2/ARLRDBD.TTF");
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
            TextView myMsg = new TextView(Login.this);
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

        fp = (TextView) findViewById(R.id.fgpwd);
        fp.setTypeface(font);
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                TextView myMsg = new TextView(Login.this);
                final EditText input = new EditText(Login.this);
                myMsg.setText("Enter your registered email id.");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setGravity(Gravity.CENTER);
                myMsg.setTextColor(Color.BLACK);
                alert.setCustomTitle(myMsg);
                alert.setView(input);
                alert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                value = input.getText().toString().trim();
                                value=value.replaceAll(" ","").trim();
                                new forget().execute();
                            }
                        });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.show();
            }
        });


        username = (EditText) findViewById(R.id.ed_lastName);
        password = (EditText) findViewById(R.id.ed_password);
        settings = getSharedPreferences(PREFS_NAME, 0);
        pwd = getSharedPreferences(Password, 0);
        btn = (Button) findViewById(R.id.btn_Submit);
        btn.setTypeface(font);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();
                if (user.matches("") || pass.matches("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
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
                    username.setText("");
                    password.setText("");
                } else {
                    new log().execute();
                }
            }
        });
    }

    private void displayMessage(Profile profile) {
        if (profile != null) {
            fbname = profile.getName();
            fbname=fbname.replaceAll(" ","").trim();
            userfb = fbname;
            System.out.println(profile.getName());
        }
    }

    private void getFbKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.administrator.renudadlani",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    private void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    /*    facebook login
    * */
    private void getLoginDetails(LoginButton login_button) {
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {

                GraphRequest request = GraphRequest.newMeRequest(
                        login_result.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    email = object.getString("email");
                                    Log.v("Email = ", " " + email);
                                    if (email.matches("dadlanirenu@hotmail.com")) {
                                        settings = getSharedPreferences(PREFS_NAME, 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("logged", "logged");
                                        editor.putString("username",email);
                                        editor.putString("dadlanirenu@hotmail.com", "dadlanirenu@hotmail.com");
                                        editor.commit();
                                        new regi().execute();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                        TextView myMsg = new TextView(Login.this);
                                        myMsg.setText("Congratulations!");
                                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                                        myMsg.setTextSize(20);
                                        myMsg.setTextColor(Color.BLACK);
                                        builder.setCustomTitle(myMsg);
                                        builder.setMessage("You have logged in successfully.");
                                        builder.setPositiveButton("Continue.",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {
                                                        Intent it = new Intent(Login.this, Home.class);
                                                        startActivity(it);
                                                    }
                                                });

                                        builder.show();
                                    }
                                    else {

                                        settings = getSharedPreferences(PREFS_NAME, 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("logged", "logged");
                                        editor.putString("username",email);
                                        editor.commit();
                                        new regi().execute();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                        TextView myMsg = new TextView(Login.this);
                                        myMsg.setText("Congratulations!");
                                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                                        myMsg.setTextSize(20);
                                        myMsg.setTextColor(Color.BLACK);
                                        builder.setCustomTitle(myMsg);
                                        builder.setMessage("You have logged in successfully.");
                                        builder.setPositiveButton("Continue.",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {
                                                        Intent it = new Intent(Login.this, Home1.class);
                                                        startActivity(it);
                                                    }
                                                });

                                        builder.show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");

                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
    }

    /*    Registration Form
    * */
    private class regi extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            fu = email;

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://renudadlani.com/mobile_app/insertdata.php?case_id=1" +
                    "&fname="+userfb+"&lname=&email=" + fu + "" +
                    "&password=&username=&token=" + token + "";
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
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.commit();


                } else if (message.equals("2")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Login failed.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(getApplicationContext(), Register.class);
                                    startActivity(it);
                                }
                            });

                    builder.show();
                } else if (message.equals("0")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
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
                                    Intent it = new Intent(getApplicationContext(), Login.class);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data", data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class log extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            user = username.getText().toString().trim();
            user = user.replaceAll(" ", "");
            user = user.replaceAll("\n", "");
            pass = password.getText().toString().trim();
            pass = pass.replaceAll(" ", "");
            pass = pass.replaceAll("\n", "");
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://renudadlani.com/mobile_app/insertdata.php?case_id=2&email=" + user + "&password=" + pass + "";
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

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.putString("user",user);
                    editor.commit();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have logged in successfully.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    if (user.matches("dadlanirenu@hotmail.com") && pass.matches("admin")) {
                                        Intent it = new Intent(getApplicationContext(), Home.class);
                                        it.putExtra("name", user);
                                        startActivity(it);
                                        pwd = getSharedPreferences(Password, 0);
                                        SharedPreferences.Editor editor2 = settings.edit();
                                        editor2.putString("admin", "admin");
                                        editor2.commit();
                                    } else {

                                        Intent it = new Intent(getApplicationContext(), Home1.class);
                                        it.putExtra("name", user);
                                        startActivity(it);
                                        SharedPreferences.Editor editor2 = settings.edit();
                                        editor2.commit();
                                    }

                                }
                            });

                    builder.show();

                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
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

    public void onBackPressed() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));

    }

    private class forget extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            fu1 = value;
            fu1 = fu1.replaceAll(" ", "");
            fu1 = fu1.replaceAll("\n", "");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://renudadlani.com/mobile_app/insertdata.php?case_id=4?username=&email="+fu1+"&password=";
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
                    settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.commit();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Alert!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Password sent successfully to registered email id.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    TextView myMsg = new TextView(Login.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Email is not valid.");
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



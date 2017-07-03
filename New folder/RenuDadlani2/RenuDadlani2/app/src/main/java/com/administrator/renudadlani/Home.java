package com.administrator.renudadlani;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Home extends AppCompatActivity {
    public static final String MESSAGE_RECEIVED = "message_received";
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb = "neha@outsourcingservicesusa.com";
    SharedPreferences settings, pwd;
    final Context context = this;
    String shareURL="http://www.renudadlani.com";
    EditText username, password;
    ImageView iv_home, iv_about, iv_collection, iv_media,
            iv_contactus, iv_notification, iv_face, iv_insta,iv_link,iv_twit,iv_pin,im_logout;
    String username1,mes,email1,message1;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhome);
        Intent it=getIntent();
        username1 = it.getStringExtra("name");

        Intent intent = getIntent();
        String lName = intent.getStringExtra("message");
        if(lName != null && !lName.isEmpty())
        {
            intent.getAction().equals(MESSAGE_RECEIVED);
            String message = intent.getStringExtra("message");
            showAlertDialog(message);
        }
        else
        {
        }
        settings = getSharedPreferences(PREFS_NAME, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        iv_home = (ImageView) findViewById(R.id.i_home);
        iv_about = (ImageView) findViewById(R.id.i_about);
        iv_collection = (ImageView) findViewById(R.id.i_collection);
        iv_media = (ImageView) findViewById(R.id.i_media);
        iv_contactus = (ImageView) findViewById(R.id.i_contact);
        iv_notification = (ImageView) findViewById(R.id.i_notification);
        iv_face = (ImageView) findViewById(R.id.i_facebook);
        iv_insta = (ImageView) findViewById(R.id.i_whatsapp);
        iv_link = (ImageView) findViewById(R.id.i_link);

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Homeactivity.class);
                startActivity(intent);
            }
        });

        iv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Aboutactivity.class);
                startActivity(intent);
            }
        });

        iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Collectionactivity.class);
                startActivity(intent);
            }
        });
        iv_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Mediaactivity.class);
                startActivity(intent);
            }
        });
        iv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Contact.class);
                startActivity(intent);
            }
        });
        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Notifyall.class);
                startActivity(intent);
            }
        });
        iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/pages/Renu-Dadlani/114144755433339"));
                startActivity(intent);
            }
        });
        iv_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareinWhatsapp();
            }
        });
        iv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/in/renu-dadlani-1b1122119"));
                startActivity(intent);
            }
        });

    }

    private void showAlertDialog(String message) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog);
        TextView txt = (TextView) dialog.findViewById(R.id.textView);
        txt.setText(message);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);
        Button dialogButton1 = (Button) dialog.findViewById(R.id.dialogButton1);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=(EditText)dialog.findViewById(R.id.editText1);
                mes=username.getText().toString();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void shareinWhatsapp() {
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");
        waIntent.setPackage("com.whatsapp");
        if (waIntent != null) {
            waIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    shareURL);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } else
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (settings.getString("logged", "").toString().equals("logged")) {
            if (settings.getString("admin", "").toString().equals("admin") || settings.getString("dadlanirenu@hotmail.comm", "").toString().equals("dadlanirenu@hotmail.com")) {
                Intent intent = new Intent(Home.this, Home.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Home.this, Home1.class);
                startActivity(intent);
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("admin");
        editor.remove("logged");
        editor.commit();
        LoginManager.getInstance().logOut();

    }

    private class sentNotice extends AsyncTask<String, String, String> {
        protected void onPreExecute()
        {
            super.onPreExecute();
            email1 = "61";
            message1 = mes;
            message1 = message1.replaceAll(" ", "%20").trim();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://renudadlani.com/mobile_app/gg.php?case_id=2&userid=61&message=" + message1 + "&admin=dadlanirenu@gmail.com&username=";
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
                String message = jsonResult.getString("success");

                Log.e("Success: ", message);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

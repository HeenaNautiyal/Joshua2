package com.administrator.renudadlani;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Home1 extends AppCompatActivity {
    ArrayList<Actors> actorsList;
    ActorAdapter adapter;
    Uri URI = null;
    ImageView iv_home, iv_about, iv_collection, iv_media,
            iv_contactus, iv_face, iv_insta,iv_link,iv_twit,iv_pin,im_logout;;
    public static final String MESSAGE_RECEIVED = "message_received";
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb="neha@outsourcingservicesusa.com";
    SharedPreferences settings,pwd;
    EditText username, password;
    final Context context = this;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String token,mes,email1,message1,username1,adminfont;
    String email, subject,fu,fu1,value,token1,message;
    String shareURL="http://www.renudadlani.com";
    String shareWhatsappURL="https://play.google.com/store/apps/details?id=com.administrator.renudadlani&hl=en";
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhome1);
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
        adapter = new ActorAdapter(getApplicationContext(), R.layout.row1, actorsList);

        settings  = getSharedPreferences(PREFS_NAME, 0);
        adminfont=settings.getString("user","");
        mRegistrationBroadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS))
                {
                    token = intent.getStringExtra("token");
                }
            }
        };
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        iv_home = (ImageView) findViewById(R.id.i_home);
        iv_about = (ImageView) findViewById(R.id.i_about);
        iv_collection = (ImageView) findViewById(R.id.i_collection);
        iv_media = (ImageView) findViewById(R.id.i_media);
        iv_contactus = (ImageView) findViewById(R.id.i_contact);
        iv_face = (ImageView) findViewById(R.id.i_facebook);
        iv_insta = (ImageView) findViewById(R.id.i_whatsapp);
        iv_link = (ImageView) findViewById(R.id.i_link);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home1.this, Homeactivity.class);
                startActivity(intent);
            }
        });

        iv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home1.this, Aboutactivity.class);
                startActivity(intent);
            }
        });
        iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home1.this, Collectionactivity.class);
                startActivity(intent);
            }
        });
        iv_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home1.this, Mediaactivity.class);
                startActivity(intent);
            }
        });
        iv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home1.this, Contact.class);
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

    private void showAlertDialog(final String message) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog);
        TextView txt = (TextView) dialog.findViewById(R.id.textView);
        txt.setText(message);
        dialog.setTitle("Renu Updates!");
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
                adminfont=settings.getString("user","");
                sendmail();
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
                    shareWhatsappURL);
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

    protected void onDestroy(){
        super.onDestroy();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("admin");
        editor.remove("logged");
        editor.commit();

    }
    private void sendmail() {
        email = " dadlanirenu@hotmail.com";
        subject ="You have a new enquiry!";
        final Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                subject);
        if (URI != null) {
            emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
        }
        emailIntent
                .putExtra(android.content.Intent.EXTRA_TEXT, message);
        this.startActivity(Intent.createChooser(emailIntent,
                "Sending email..."));
    }

}



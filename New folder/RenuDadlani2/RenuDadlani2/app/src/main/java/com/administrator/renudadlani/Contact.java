package com.administrator.renudadlani;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Contact extends AppCompatActivity {
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    TextView textView2;
    Button mail,call;
    String email, subject, message;
    Uri URI = null;
    ImageView im_logout;
    CallbackManager callbackManager;
    SharedPreferences settings,pwd;
    ImageView iv_face,iv_insta,iv_link,iv_twit,iv_pin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_contactus);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(
                R.drawable.gradient));
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts2/ARLRDBD.TTF");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setTypeface(tf);
        mail=(Button)findViewById(R.id.btnemail);
        mail.setTypeface(tf);
        call=(Button)findViewById(R.id.btncall);
        call.setTypeface(tf);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmail();

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonecall();
            }
        });
        im_logout=(ImageView)findViewById(R.id.action_Logout2);
        iv_face = (ImageView) findViewById(R.id.i_facebook);
        iv_insta = (ImageView) findViewById(R.id.i_insta);
        iv_link = (ImageView) findViewById(R.id.i_link);
        iv_twit = (ImageView) findViewById(R.id.i_twitter);
        iv_pin = (ImageView) findViewById(R.id.i_pin);
        im_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Contact.this);
                TextView myMsg = new TextView(Contact.this);
                myMsg.setText("Log out!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);

                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Are you Sure ?");
                builder.setPositiveButton("Yes,Logout.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.remove("admin");
                                editor.remove("logged");
                                editor.commit();
                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(Contact.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });

                builder.show();
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
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/renu_dadlani"));
                startActivity(intent);
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
        iv_twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/renudadlani12"));
                startActivity(intent);
            }
        });
        iv_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.pinterest.com/handembroid0022/"));
                startActivity(intent);
            }
        });




    }

    private void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    private void phonecall() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Contact.this);
        TextView myMsg = new TextView(Contact.this);
        myMsg.setText("+919810052661");
        myMsg.setTextSize(20);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);

        builder.setCustomTitle(myMsg);

        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setPositiveButton("call",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String number="+919810052661";
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+number));
                        startActivity(callIntent);
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
            }
        });

        builder.show();

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
    public void onResume() {
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
}


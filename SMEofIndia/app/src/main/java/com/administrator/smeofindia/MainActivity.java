package com.administrator.smeofindia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
 ImageView iv_login,iv_register;
    private static final String TWITTER_KEY = "9s6tM2Jmld5xH5NjXmx6OJcsp";
    private static final String TWITTER_SECRET = "33jdmlKsIbjmuqokuPOqq4ZhSSdNQULZjJOXMB7wqVuwSh1uBn";
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    String email,password,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric with = Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(PREFS_NAME, 0);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().hide();
        View view =getSupportActionBar().getCustomView();


        user=settings.getString("user",email);
        if(user == null){
            //Toast.makeText(getApplicationContext(),"u have to login first",Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent it= new Intent(MainActivity.this,Mem.class);
            startActivity(it);
        }

        iv_login=(ImageView)findViewById(R.id.login);
        iv_register=(ImageView)findViewById(R.id.Register);
        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Loginmem.class);
                startActivity(intent);
            }
        });
        iv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,Regidmem.class);
                startActivity(intent);
            }
        });
    }
}

package com.administrator.renudadlani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Splash extends AppCompatActivity{
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb="neha@outsourcingservicesusa.com";
    SharedPreferences settings,pwd;

    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        settings  = getSharedPreferences(PREFS_NAME, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
    protected void onDestroy(){
        super.onDestroy();

    }
}

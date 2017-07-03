package com.administrator.smeofindia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 11/19/2016.
 */
public class Mem extends AppCompatActivity{
    boolean doubleBackToExitPressedOnce = false;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    String email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
    }

    public void onPause(){
        super.onPause();
      /*  SharedPreferences.Editor editor = settings.edit();
        editor.putString("logged", "logged");
        editor.putString("username",email);
        editor.commit();*/
    }


    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

                SharedPreferences.Editor editor = settings.edit();
                editor.remove("logged");
                editor.remove("user");
                editor.commit();

            }
        }, 2000);
    }


}

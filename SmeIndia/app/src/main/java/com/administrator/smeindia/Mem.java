package com.administrator.smeindia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 11/23/2016.
 */
public class Mem extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    String email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
    }
}

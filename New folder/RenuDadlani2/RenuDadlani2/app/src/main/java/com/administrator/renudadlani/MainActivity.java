package com.administrator.renudadlani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2;
    Button btnrg, btnlg;
    CheckBox chk;

    public static final String PREFS_NAME = "LoginPrefs";

    public static final String Password = "admin";
    public static final String fb="dadlanirenu@hotmail.com";
    SharedPreferences settings,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts2/ARLRDBD.TTF");
        setContentView(R.layout.activity_main);
        settings  = getSharedPreferences(PREFS_NAME, 0);
        pwd=getSharedPreferences(Password,0);
        btnrg = (Button) findViewById(R.id.register);
        btnrg.setTypeface(font);
        btnlg = (Button) findViewById(R.id.login);
        btnlg.setTypeface(font);

        if (settings.getString("logged", "").toString().equals("logged"))
        {
            if(settings.getString("admin", "").toString().equals("admin")||settings.getString("dadlanirenu@hotmail.com", "").toString().equals("dadlanirenu@hotmail.com")) {

                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, Home1.class);
                startActivity(intent);
            }
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Register.class);
                startActivity(it);
            }
        });
        btnlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Login.class);
                startActivity(it);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        if (settings.getString("logged", "").toString().equals("logged"))
        {
            if(settings.getString("admin", "").toString().equals("admin")||settings.getString("dadlanirenu@hotmail.com", "").toString().equals("dadlanirenu@hotmail.com")) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, Home1.class);
                startActivity(intent);
            }
        }
    }

    protected void onRestart()
    {
        super.onRestart();
        if (settings.getString("logged", "").toString().equals("logged"))
        {
            if(settings.getString("admin", "").toString().equals("admin")||settings.getString("dadlanirenu@hotmail.com", "").toString().equals("dadlanirenu@hotmail.com")) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, Home1.class);
                startActivity(intent);
            }
        }
    }
    public void onBackPressed() {
        moveTaskToBack(true);
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

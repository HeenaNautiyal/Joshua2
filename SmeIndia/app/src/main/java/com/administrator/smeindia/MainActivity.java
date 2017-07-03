package com.administrator.smeindia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView iv_login,iv_register;
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    String email,password,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(PREFS_NAME, 0);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();
        user=settings.getString("user",email);
        if(user == null){}
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

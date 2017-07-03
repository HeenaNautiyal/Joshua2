package com.administrator.bizhawkz.d2d;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.d2d.R;

public class UploadPresc extends AppCompatActivity {
    ImageView upload;
    Button btnload;
    TextView sk;
    String aaa,bbb,ccc,ddd;
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_presc);

        settings = getSharedPreferences(PREFS_NAME, 0);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        upload=(ImageView)findViewById(R.id.image_upload);
        btnload=(Button)findViewById(R.id.btn_continue2);

        if (settings.getString("logged", "").toString().equals("logged")) {
            aaa=settings.getString("username","");
        }

        Bundle b = getIntent().getExtras();
        aaa=(String)b.getCharSequence("name");
        sk=(TextView)findViewById(R.id.tv_skip);
        sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(UploadPresc.this,MedicinesContent.class);
                Bundle b = new Bundle();
                b.putString("name", aaa);
                it.putExtras(b);
                startActivity(it);

            }
        });
        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UploadPresc.this, "Prescription uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.administrator.bizhawkz.d2d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.administrator.d2d.R;

/**
 * Created by Administrator on 11/29/2016.
 */
public class RegisterOption extends AppCompatActivity {
    ImageView Chem,user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);


        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();
        Chem=(ImageView)findViewById(R.id.btn_chemist);
        user=(ImageView)findViewById(R.id.btn_user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(RegisterOption.this,Register.class);
                startActivity(it);
            }
        });
        Chem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(RegisterOption.this,CheRegis.class);
                startActivity(it);
            }
        });

    }
}

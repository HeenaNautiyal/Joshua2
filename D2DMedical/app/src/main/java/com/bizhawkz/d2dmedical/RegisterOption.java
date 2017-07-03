package com.bizhawkz.d2dmedical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegisterOption extends AppCompatActivity {
    ImageView Chem,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_option);
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
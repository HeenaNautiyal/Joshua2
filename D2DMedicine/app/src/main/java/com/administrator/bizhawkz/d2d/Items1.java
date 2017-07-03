package com.administrator.bizhawkz.d2d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Items1 extends AppCompatActivity {
ImageView upload,presc,Every;
    String browse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items1);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        upload=(ImageView)findViewById(R.id.im1);
        presc=(ImageView)findViewById(R.id.im2);
        Every=(ImageView)findViewById(R.id.im3);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(Items1.this,UploadPresc.class);
                startActivity(it);
            }
        });

    }
}

package com.heena.testingfirebasecloud;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.heena.testingfirebasecloud.R;

public class Addressadd extends AppCompatActivity {
ImageView iv;
    Button btn;
    String aaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressadd);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        Bundle b = getIntent().getExtras();
        aaa=(String)b.getCharSequence("name");

        iv=(ImageView)findViewById(R.id.imgback);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(Addressadd.this,UploadPresc.class);
                Bundle b = new Bundle();
                b.putString("name", aaa);
                it.putExtras(b);
                startActivity(it);
            }
        });
    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

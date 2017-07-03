package com.heena.D2dmedicine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.util.HashMap;

public class Summery extends AppCompatActivity {

   ImageView btnback;
    SessionManager1 session;
    String mail;
    TextView tv;
    LinearLayout btnotc,btnpres,btndaily,btnbaby,btnfirst;

    String TAG="MainActivity",fname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summery);
        tv=(TextView)findViewById(R.id.textView4);

        btnback = (ImageView) findViewById(R.id.imgback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent it= new Intent(Summery.this,UploadMedicine.class);
                    startActivity(it);
            }
        });

        session = new SessionManager1(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        mail = user.get(SessionManager1.KEY_EMAIL);
        tv.setText(mail);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        btnotc=(LinearLayout) findViewById(R.id.linearlayout11);
        btnpres=(LinearLayout)findViewById(R.id.linearlayout12);
        btndaily=(LinearLayout)findViewById(R.id.linearlayout15);
        btnbaby=(LinearLayout)findViewById(R.id.linearlayout13);
        btnfirst=(LinearLayout)findViewById(R.id.linearlayout14);

        btnotc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Summery.this,Test.class);
                Bundle b = new Bundle();
                b.putString("email", "OTC (Over The Counter)");
                it.putExtras(b);
                startActivity(it);

            }
        });
        btnpres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Summery.this,Test1.class);
                Bundle b = new Bundle();
                b.putString("email", "Prescribed");
                it.putExtras(b);
                startActivity(it);

            }
        });
        btndaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Summery.this,Test2.class);
                Bundle b = new Bundle();
                b.putString("email", "Daily Care");
                it.putExtras(b);
                startActivity(it);

            }
        });
        btnbaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Summery.this,Test3.class);
                Bundle b = new Bundle();
                b.putString("email", "Baby Care");
                it.putExtras(b);
                startActivity(it);
            }
        });
        btnfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Summery.this,Test4.class);
                Bundle b = new Bundle();
                b.putString("email", " First Aid");
                it.putExtras(b);
                startActivity(it);
            }
        });

    }
}

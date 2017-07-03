package com.heena.testingfirebasecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.util.HashMap;

public class OrderPlace1 extends AppCompatActivity {
TextView tv,city,address;
    String mail1,address1,city1;
    SessionManager1 session;
    ImageView iv,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place1);

        tv=(TextView)findViewById(R.id.mail2);
        city=(TextView)findViewById(R.id.edcity);
        address=(TextView)findViewById(R.id.edaddress);
        back=(ImageView)findViewById(R.id.imgback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(OrderPlace1.this,Main2Activity.class);
                startActivity(it);
            }
        });

        iv=(ImageView)findViewById(R.id.close1);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(OrderPlace1.this,Main2Activity.class);
                startActivity(it);
            }
        });

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();


        Bundle b= getIntent().getExtras();
        address1= b.getString("address");
        city1= (String) b.getCharSequence("city");

        session = new SessionManager1(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        mail1 = user.get(SessionManager1.KEY_EMAIL);
        tv.setText(mail1);
        address.setText(address1);
        city.setText(city1);


    }
}

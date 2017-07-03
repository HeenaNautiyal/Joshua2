package com.heena.customeservices;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    //Toolbar toolbar ;
    Button btn;
  //  TextView latLongTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setNavigationIcon(R.mipmap.icontext);
        toolbar.setTitle("Customer");
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.geo));
        setSupportActionBar(toolbar);


        latLongTV = (TextView) findViewById(R.id.textview);*/
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(MainActivity.this,SecondActivity.class);
                startActivity(it);
               /* EditText editText = (EditText) findViewById(R.id.editText);
                String address = editText.getText().toString();
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getApplicationContext(), new GeocoderHandler());*/
            }
        });
    }


   /* private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            latLongTV.setText(locationAddress);
        }
    }*/
}

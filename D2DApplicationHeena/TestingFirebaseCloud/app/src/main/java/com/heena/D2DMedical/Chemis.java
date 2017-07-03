package com.heena.D2DMedical;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.util.HashMap;

public class Chemis extends AppCompatActivity {
    Button btn_continue;
    TextView number,license,state,city,name,address,email,expiery;
    String aaa,bbb,ccc,ddd,eee,fff,ggg,hhh,iii,pin,mail1,mail;
    ImageView btnback;
    private CoordinatorLayout coordinatorLayout;
    SessionManager1 session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist_profile);
        /*getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();*/
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        session = new SessionManager1(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        mail1 = user.get(SessionManager1.KEY_EMAIL);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.door);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        ImageView circularImageView = (ImageView)findViewById(R.id.imgProfilePicture);
        circularImageView.setImageBitmap(circularBitmap);
        number=(TextView)findViewById(R.id.number);
        address=(TextView)findViewById(R.id.Address);
        email=(TextView)findViewById(R.id.email);
        name=(TextView)findViewById(R.id.name);
        license=(TextView)findViewById(R.id.license);
        expiery=(TextView)findViewById(R.id.expiry);
        state=(TextView)findViewById(R.id.state);
        btnback=(ImageView)findViewById(R.id.imgback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Chemis.this,Nearchemeist.class);
                Bundle b = new Bundle();
                b.putString("pincode", pin);
                it.putExtras(b);
                startActivity(it);
            }
        });
        btn_continue=(Button)findViewById(R.id.btn_continue);
       /* getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();*/

        Bundle b = getIntent().getExtras();
        aaa=(String)b.getCharSequence("name");
        ccc=(String)b.getCharSequence("address");
        ddd=(String)b.getCharSequence("city");
        eee=(String)b.getCharSequence("state");
        fff=(String)b. getCharSequence("mobileno");
        ggg=(String)b. getCharSequence("licenseno");
        iii=(String)b. getCharSequence("expirydate");
        pin=(String)b.getCharSequence("pincode");
        mail=(String)b.getCharSequence("email2");
        session.createChemistLogin(mail.replaceAll(" ", ""));


        name.setText(aaa);
        email.setText(mail);
        license.setText(ggg);
        number.setText(fff);
        state.setText(eee);
        address.setText(ccc);
        expiery.setText(iii);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Chemis.this);
                TextView myMsg = new TextView(Chemis.this);
                myMsg.setText("Alert!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Are you sure to Proceed with Chemist");
                builder.setPositiveButton("ACCEPT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                Intent it = new Intent(Chemis.this,UploadPresc.class);
                                Bundle b = new Bundle();
                                b.putString("name", aaa);
                                b.putString("address",bbb);
                                b.putString("email",mail);
                                it.putExtras(b);
                                startActivity(it);
                            }
                        });
                builder.setNegativeButton("DECLINE",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        Intent it = new Intent(Chemis.this,Nearchemeist.class);
                        Bundle b = new Bundle();
                        b.putString("pincode", pin);
                        it.putExtras(b);
                        startActivity(it);
                    }
                });
                builder.show();
            }
        });
    }
}

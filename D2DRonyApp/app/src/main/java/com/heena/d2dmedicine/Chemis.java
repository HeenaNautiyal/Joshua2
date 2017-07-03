package com.heena.d2dmedicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heena.d2dronyapp.Nearchemeist;
import com.example.heena.d2dronyapp.R;
import com.example.heena.d2dronyapp.UploadPresc;

public class Chemis extends AppCompatActivity {

    Button btn_continue;
    TextView tv,add,state,city,name;
    String aaa,bbb,ccc,ddd,eee;
    ImageView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemis);
       // tv=(TextView)findViewById(R.id.textChem);
        add=(TextView)findViewById(R.id.number);
        state=(TextView)findViewById(R.id.Address);
        city=(TextView)findViewById(R.id.email1);
        name=(TextView)findViewById(R.id.name);
        btn=(ImageView)findViewById(R.id.backbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* Intent It =new Intent(Chemis.this,Nearchemeist.class);
               startActivity(It);*/
            }
        });

        btn_continue=(Button)findViewById(R.id.btn_continue1);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        Bundle b = getIntent().getExtras();
        aaa=(String)b.getCharSequence("name");
        ccc=(String)b.getCharSequence("address");
        ddd=(String)b.getCharSequence("city");
        eee=(String)b.getCharSequence("state");

       // tv.setText(aaa);
        name.setText(aaa);
        add.setText(ccc);
        state.setText(eee);
        city.setText(ddd);


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Chemis.this);
                TextView myMsg = new TextView(Chemis.this);
                myMsg.setText("Warning!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Are you sure to Proceed with Chemist!");
                builder.setPositiveButton("ACCEPT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                Intent it = new Intent(Chemis.this,UploadPresc.class);
                                Bundle b = new Bundle();
                                b.putString("name", aaa);
                                b.putString("address",bbb);
                                it.putExtras(b);
                                startActivity(it);
                            }
                        });
                builder.setNegativeButton("DECLINE",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent it = new Intent(Chemis.this,Nearchemeist.class);
                        startActivity(it);
                    }
                });
                builder.show();
            }
        });
    }

}

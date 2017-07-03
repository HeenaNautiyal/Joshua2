package com.administrator.bizhawkz.d2d;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.administrator.d2d.R;

public class Chemis extends AppCompatActivity {
    Button btn_continue;
    TextView tv,add,state,city,name;
    String aaa,bbb,ccc,ddd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemis);
        tv=(TextView)findViewById(R.id.textChem);
        add=(TextView)findViewById(R.id.number);
        state=(TextView)findViewById(R.id.Address);
        city=(TextView)findViewById(R.id.email1);
        name=(TextView)findViewById(R.id.name);


        btn_continue=(Button)findViewById(R.id.btn_continue1);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        Bundle b = getIntent().getExtras();
        aaa=(String)b.getCharSequence("name");
        bbb=(String)b.getCharSequence("address");
        ccc=(String)b.getCharSequence("state") ;
        ddd=(String)b.getCharSequence("city") ;

        tv.setText(b.getCharSequence("name"));
        name.setText(b.getCharSequence("name"));
        add.setText(b.getCharSequence("address"));
        state.setText(b.getCharSequence("state"));
        city.setText(b.getCharSequence("city"));

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
                        Intent it = new Intent(Chemis.this,DemoChem.class);
                        startActivity(it);
                    }
                });
                builder.show();
            }
        });
    }

}

package com.heena.testingfirebasecloud;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.heena.testingfirebasecloud.R;

import java.util.ArrayList;
import java.util.List;

public class Ordernow extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
String abc,item1,quan,med,item;
    TextView tvmed;
    EditText edquan;
    TextView ed;
    Button  OK1,btn,chk1;
    String stfinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordernow);
        Bundle b = getIntent().getExtras();

        abc= (String) b.getCharSequence("medicine");

        tvmed=(TextView)findViewById(R.id.medicine);

        tvmed.setText(abc);

        edquan=(EditText)findViewById(R.id.edquantity);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        btn=(Button)findViewById(R.id.btn_continue);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item1=item;
                med=abc;
                quan=edquan.getText().toString();
                stfinal="Medicine :"+med+"\n" +"Quantity :"+quan;
                //Toast.makeText(getApplicationContext(), "Your Order is: \n" + med+"\n"+quan+"\n"+item1, Toast.LENGTH_LONG).show();
                buildDialog(R.style.DialogTheme, "Left - Right Animation!");


            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("Weekly");
        categories.add("15days");
        categories.add("Monthly");
        categories.add("2Month");
        categories.add("3Month");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void startAlert() {
        if(item1=="daily"){
            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (1*6000), pendingIntent);
           /* alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (1 *24*3600000), pendingIntent);*/
           // Toast.makeText(this, "Alarm set in " + 1 + " seconds",Toast.LENGTH_LONG).show();
        }
       /* EditText text = (EditText) findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());*/

    }

    private void buildDialog(int animationSource, String s) {
        LayoutInflater layoutInflater = LayoutInflater.from(Ordernow.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog2, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ordernow.this);
        alertDialogBuilder.setView(promptView);

        //final AlertDialog show = alertDialogBuilder.show();
        final AlertDialog alert = alertDialogBuilder.create();

        OK1 = (Button) promptView.findViewById(R.id.btn_ok);
        ed = (TextView) promptView.findViewById(R.id.edittext);
        ed.setText(stfinal);
        OK1 = (Button) promptView.findViewById(R.id.btn_ok);
        OK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent it = new Intent(Ordernow.this,Main2Activity.class);
                startActivity(it);

            }
        });
        chk1=(Button)promptView.findViewById(R.id.btn_chk);
        chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Ordernow.this,OrderList.class);
                startActivity(it);
            }
        });


        alert.getWindow().getAttributes().windowAnimations = animationSource;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;

        alert.show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

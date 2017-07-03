package com.heena.d2dmedicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.d2dronyapp.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Confirm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String abc,name, fname;
    TextView a,c,d;
    EditText e;
    Spinner spinner1;
    String Med,Menu,Comp,aaa,adminfont,item,city,state;
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        addListenerOnSpinnerItemSelection();

        settings = getSharedPreferences(PREFS_NAME, 0);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        if (settings.getString("logged", "").toString().equals("logged")) {
            adminfont=settings.getString("email",null);
        }
        Bundle b = getIntent().getExtras();

         abc= (String) b.getCharSequence("Medicine");
        name= (String) b.getCharSequence("Menufacture");
        fname= (String) b.getCharSequence("Added");

        Toast.makeText(getApplicationContext(),fname,Toast.LENGTH_LONG).show();
        btn=(Button)findViewById(R.id.btn_continue24);

        a=(TextView)findViewById(R.id.tv1);
        d =(TextView)findViewById(R.id.tv2);
        c=(TextView)findViewById(R.id.teview);
        a.setText(abc);
        d.setText(name);
        c.setText(fname);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  new Addmed().execute();
                 }
        });
    }

    private void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.Spinenr);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private class Addmed extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             aaa = spinner1.getSelectedItem().toString();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            String url ="http://seajob.net/medicalapp/insertdata.php?caseid=14&medicinename="+abc.replaceAll(" ","%20")+"&manufacture="+name.replaceAll(" ","%20")+"&address=test6&quantity="+aaa+"&email="+adminfont.replaceAll(" ","%20")+"";
            String SetServerString = "";
             HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = httpClient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + SetServerString);
            return SetServerString;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                //pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Confirm.this);
                    TextView myMsg = new TextView(Confirm.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Your order has been successfully update to chemist");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                  /*  Intent it = new Intent(Confirm.this, DailyCare.class);
                                    startActivity(it);*/
                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Confirm.this);
                    TextView myMsg = new TextView(Confirm.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Email/Password is invalid.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

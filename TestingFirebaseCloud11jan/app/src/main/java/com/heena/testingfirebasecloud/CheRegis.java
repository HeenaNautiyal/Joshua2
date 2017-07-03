package com.heena.testingfirebasecloud;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CheRegis extends AppCompatActivity {

    Button submit;
    ImageView iv;
    ProgressDialog pb;
    SharedPreferences settings,pwd;
    EditText fname, lname, email, password, number, pincode, licence, date,addr,city,state;
    String fnamw1, lname1, email1, password1, number1, pincode1, lcnumber, date1,addr1,city1,state1,Currentdate;
    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private static final int REQUEST_CODE_PERMISSION = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_che_regis);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();
        // pb = new ProgressDialog(Register.this);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
        submit = (Button) findViewById(R.id.btn_Submit);
        fname = (EditText) findViewById(R.id.ef_fname);
        lname = (EditText) findViewById(R.id.ef_lstname);
        email = (EditText) findViewById(R.id.ed_email);
        password = (EditText) findViewById(R.id.ed_password);
        number = (EditText) findViewById(R.id.ed_mobile);
        pincode = (EditText) findViewById(R.id.ed_pincode);
        licence = (EditText) findViewById(R.id.ed_license);
        addr=(EditText)findViewById(R.id.ed_address);
        city=(EditText)findViewById(R.id.ed_city);
        state=(EditText)findViewById(R.id.ed_state);
        iv=(ImageView)findViewById(R.id.imgback);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CheRegis.this,Login.class);
                startActivity(it);
            }
        });


        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    fromDatePickerDialog.show();
                }else {                                 }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnamw1 = fname.getText().toString();
                lname1 = lname.getText().toString();
                email1 = email.getText().toString();
                password1 = password.getText().toString();
                number1 = number.getText().toString();
                pincode1 = pincode.getText().toString();
                lcnumber  = licence.getText().toString();
                date1 = date.getText().toString();
                addr1 = addr.getText().toString();
                city1=city.getText().toString();
                state1=state.getText().toString();


                AlertDialog.Builder builder = new AlertDialog.Builder(CheRegis.this);
                TextView myMsg = new TextView(CheRegis.this);
                myMsg.setText("Congratulations!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("You have logged in successfully.");
                builder.setPositiveButton("Continue.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Registration().execute();
                            }

                        });
                builder.show();
            }
        });
    }



    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    private void findViewsById() {
        date = (EditText) findViewById(R.id.ed_expier);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();
    }

    private class Registration extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            fnamw1 = fname.getText().toString();
            lname1 = lname.getText().toString();
            email1 = email.getText().toString();
            password1 = password.getText().toString();
            number1 = number.getText().toString();
            pincode1 = pincode.getText().toString();
            lcnumber = licence.getText().toString();
            date1 = date.getText().toString();
            addr1=addr.getText().toString();
            city1=city.getText().toString();
            state1=state.getText().toString();

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();

            String url = "http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=4&fname="+fnamw1.replaceAll(" ", "")+"&lname="+lname1.replaceAll(" ", "")+"&email="+email1.replaceAll(" ", "")+"&password="+password1.replaceAll(" ", "")+"&mobileno="+number1+"&address="+addr1.replaceAll(" ","")+"&city="+city1.replaceAll(" ","")+"&state="+state1.replaceAll(" ","%")+"&pincode="+pincode1+"&licenseno="+lcnumber.replaceAll(" ", "")+"&expirydate="+date1.replaceAll(" ", "")+"";

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

        protected void onPostExecute(String result) {
            try {
                //pb.dismiss();
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    /*SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.commit();*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheRegis.this);
                    TextView myMsg = new TextView(CheRegis.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have logged in successfully.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    fname.setText("");
                                    lname.setText("");
                                    email.setText("");
                                    password.setText("");
                                    number.setText("");
                                    pincode.setText("");
                                    licence.setText("");
                                    date.setText("");
                                    addr.setText("");
                                    city.setText("");
                                    state.setText("");

                                    Intent it =new Intent(CheRegis.this,UploadMedicine.class);
                                    startActivity(it);
                                  /*  Intent it = new Intent(CheRegis.this, SearchChemist.class);
                                    startActivity(it);*/
                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(CheRegis.this);
                    TextView myMsg = new TextView(CheRegis.this);
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
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

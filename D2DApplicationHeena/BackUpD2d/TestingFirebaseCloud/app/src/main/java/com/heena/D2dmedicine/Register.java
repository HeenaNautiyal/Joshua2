package com.heena.D2dmedicine;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
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

public class Register extends AppCompatActivity {

    Button submit;
    ImageView iv;
    ProgressDialog pb;
    SharedPreferences settings,pwd;
    EditText fname, lname, email, password, number, pincode, house, colony, city, state;
    String fnamw1, lname1, email1, password1, number1, pincode1, house1, regEx, city1, state1;


    String Expn =
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();



        submit = (Button) findViewById(R.id.btn_Submit);
        fname = (EditText) findViewById(R.id.ef_fname);
        lname = (EditText) findViewById(R.id.ef_lstname);
        email = (EditText) findViewById(R.id.ed_email);
        password = (EditText) findViewById(R.id.ed_password);
        number = (EditText) findViewById(R.id.ed_mobile);
        pincode = (EditText) findViewById(R.id.ed_pincode);
        house = (EditText) findViewById(R.id.ed_Hno);
        city = (EditText) findViewById(R.id.ed_town);
        state = (EditText) findViewById(R.id.ed_state);
        iv=(ImageView)findViewById(R.id.imgback);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Register.this,Login.class);
                startActivity(it);
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
                house1 = house.getText().toString();
                city1 = city.getText().toString();
                state1 = state.getText().toString();

                if (fnamw1.matches("") || lname1.matches("") || email1.matches("") ||
                        password1.matches("") || number1.matches("") || pincode1.matches("")
                        || house1.matches("") || city1.matches("") || state1.matches("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("All fields are mandatory.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                } else
                {
                    if (email1.matches(Expn) && email1.length() > 0) {
                        new Registration().execute();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        TextView myMsg = new TextView(Register.this);
                        myMsg.setText("Warning!");
                        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                        myMsg.setTextSize(20);

                        myMsg.setTextColor(Color.BLACK);
                        builder.setCustomTitle(myMsg);
                        builder.setMessage("Please enter a valid mail ID!");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                    }
                }
            }                            // new Registration().execute();

        });
    }

    private class Registration extends AsyncTask<String, String, String> {
        protected void onPreExecute() {

            fnamw1 = fname.getText().toString();
            lname1 = lname.getText().toString();
            email1 = email.getText().toString();
            password1 = password.getText().toString();
            number1 = number.getText().toString();
            pincode1 = pincode.getText().toString();
            house1 = house.getText().toString();
            city1 = city.getText().toString();
            state1 = state.getText().toString();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url ="http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=1" +
                    "&fname="+fnamw1.replaceAll(" ", "")+"&lname="+lname1.replaceAll(" ", "")+"" +
                    "&email="+email1.replaceAll(" ", "")+"&password="+password1.replaceAll(" ", "")+"" +
                    "&mobileno="+number1.replaceAll(" ", "")+"&pincode="+pincode1.replaceAll(" ", "")+
                    "&address="+house1.replaceAll(" ", "")+"" +
                    "&city="+city1.replaceAll(" ", "")+
                    "&state="+state1.replaceAll(" ", "")+"&type=1";
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

                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have registered successfully.");
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
                                    house.setText("");
                                    city.setText("");
                                    state.setText("");
                                    Intent it = new Intent(Register.this, Login.class);
                                    startActivity(it);

                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    TextView myMsg = new TextView(Register.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Already Registered.");
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
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }
}

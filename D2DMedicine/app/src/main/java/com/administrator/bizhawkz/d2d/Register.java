package com.administrator.bizhawkz.d2d;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.administrator.d2d.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 11/28/2016.
 */
public class Register extends Activity {
    ImageView submit;
    ProgressDialog pb;
    SharedPreferences settings,pwd;
    EditText fname, lname, email, password, number, pincode, house, colony, city, state;
    String fnamw1, lname1, email1, password1, number1, pincode1, house1, colony1, city1, state1;
    private static final int REQUEST_CODE_PERMISSION = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       // pb = new ProgressDialog(Register.this);


        submit = (ImageView) findViewById(R.id.btn_Submit);
        fname = (EditText) findViewById(R.id.ef_fname);
        lname = (EditText) findViewById(R.id.ef_lstname);
        email = (EditText) findViewById(R.id.ed_email);
        password = (EditText) findViewById(R.id.ed_password);
        number = (EditText) findViewById(R.id.ed_mobile);
        pincode = (EditText) findViewById(R.id.ed_pincode);
        house = (EditText) findViewById(R.id.ed_Hno);
        colony = (EditText) findViewById(R.id.ed_colony);
        city = (EditText) findViewById(R.id.ed_town);
        state = (EditText) findViewById(R.id.ed_state);

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
                colony1 = colony.getText().toString();
                city1 = city.getText().toString();
                state1 = state.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                TextView myMsg = new TextView(Register.this);
                myMsg.setText("Congratulations!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("You have logged in successfully.");
                builder.setPositiveButton("Continue.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               /* if(isReadStorageAllowed()) {
                                      return;
                                }
                                requestStoragePermission();*/
                               /* Intent it =new Intent(Register.this,Nearchemeist.class);
                                startActivity(it);*/
                                new Registration().execute();
                            }

                        });
                builder.show();
            }
        });
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
            house1 = house.getText().toString();
            colony1 = colony.getText().toString();
            city1 = city.getText().toString();
            state1 = state.getText().toString();

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://seajob.net/medicalapp/insertdata.php?caseid=1&fname="+fnamw1.replaceAll(" ", "")+"&lname="+lname1.replaceAll(" ", "")+"&email="+email1.replaceAll(" ", "")+"&password="+password1.replaceAll(" ", "")+"&mobilenumber="+number1+"&pincode="+pincode1+"&houseno="+house1.replaceAll(" ", "")+"&colony="+colony1.replaceAll(" ", "")+"&city="+city1.replaceAll(" ", "")+"&state="+state1.replaceAll(" ", "")+"";
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(com.administrator.d2d.Register.this);
                    TextView myMsg = new TextView(com.administrator.d2d.Register.this);
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
                                    house.setText("");
                                    colony.setText("");
                                    city.setText("");
                                    state.setText("");

                                    Intent it = new Intent(com.administrator.d2d.Register.this, SearchChemist.class);
                                    startActivity(it);

                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(com.administrator.d2d.Register.this);
                    TextView myMsg = new TextView(com.administrator.d2d.Register.this);
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


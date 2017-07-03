package com.heena.d2dmedicine;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.d2dronyapp.Chemist;
import com.example.heena.d2dronyapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Addressadd extends AppCompatActivity {
    EditText emailid,mobilenumber,pincode,address,city,state;
    String mail,number1,code,house,city2,state2,user,email;
    ArrayList<Chemist> actorsList;
    ImageView i_sub;

    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressadd);

        settings = getSharedPreferences(PREFS_NAME, 0);
        user = settings.getString("user", email);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        emailid=(EditText) findViewById(R.id.ef_mailid);
        mobilenumber=(EditText)findViewById(R.id.ef_number);
        pincode=(EditText)findViewById(R.id.ed_pincode);
        address=(EditText)findViewById(R.id.ed_address);
        city=(EditText)findViewById(R.id.ed_city);
        state=(EditText)findViewById(R.id.ed_state);
        i_sub=(ImageView)findViewById(R.id.btn_Submit);



        i_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Editing().execute();
            }
        });
    }

    private class Editing extends AsyncTask<String,String,String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mail=emailid.getText().toString();
            number1=mobilenumber.getText().toString();
            code=pincode.getText().toString();
            house=address.getText().toString();
            city2=city.getText().toString();
            state2=state.getText().toString();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url ="http://seajob.net/medicalapp/insertdata.php?caseid=18&email="+user.replaceAll(" ","%20")+"&mobileno="+number1.replaceAll(" ","%20")+"&pincode="+code.replaceAll(" ","%20")+"&address="+house.replaceAll(" ","%20")+"&city="+city2.replaceAll(" ","%20")+"&state="+state2.replaceAll(" ","%20")+"&newemail="+mail.replaceAll(" ","%20")+"";

           /* String url = "http://seajob.net/medicalapp/insertdata.php?caseid=18" +
                    "&email="+mail.replaceAll(" ","%20")+"&mobileno="+number1.replaceAll(" ","%20")+"" +
                    "&pincode="+code.replaceAll(" ","%20")+"&address="+house.replaceAll(" ","%20")+"" +
                    "&city="+city2.replaceAll(" ","%20")+"&state="+state2.replaceAll(" ","")+"";
*/


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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Addressadd.this);
                    TextView myMsg = new TextView(Addressadd.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Your address has been added Successfully.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    emailid.setText("");
                                    mobilenumber.setText("");
                                    pincode.setText("");
                                    address.setText("");
                                    city.setText("");
                                    state.setText("");

                                    /*Intent it = new Intent(Addressadd.this, SearchChemist.class);
                                    startActivity(it)*/;


                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Addressadd.this);
                    TextView myMsg = new TextView(Addressadd.this);
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

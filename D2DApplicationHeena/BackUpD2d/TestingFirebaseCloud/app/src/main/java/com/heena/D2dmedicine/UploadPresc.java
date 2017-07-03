package com.heena.D2dmedicine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadPresc extends AppCompatActivity {
    ImageView upload;
    Button btnload;
    TextView sk,email1,num,address,city1,pincode1,name;
    ImageView edt,presc;
    String aaa,ddd,mail,name1,number1,address1,city,state1,pincode,mail3,mail4;
    SharedPreferences settings, pwd;
    ArrayList<Chemist> actorsList;

    ActorAdapter adapter;
    SessionManager1 session;
    public static final String PREFS_NAME = "LoginPrefs";
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    ProgressDialog pb;
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_presc);
        settings = getSharedPreferences(PREFS_NAME, 0);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        btnload =(Button)findViewById(R.id.btn_continue);
        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent it = new Intent(UploadPresc.this,Main2Activity.class);
                startActivity(it);
            }
        });

        pb = new ProgressDialog(UploadPresc.this);

        session = new SessionManager1(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        mail4 = user.get(SessionManager1.KEY_EMAIL);

        name=(TextView)findViewById(R.id.textView3);
        email1=(TextView) findViewById(R.id.mail);
        num=(TextView)findViewById(R.id.number);
        address=(TextView)findViewById(R.id.address);
        city1=(TextView)findViewById(R.id.city);
        pincode1=(TextView)findViewById(R.id.pincode);

        new Logmem().execute("http://outsourcingservicesusa.com/d2d/insertdata.php?caseid=19&email="+mail4+"");

        presc=(ImageView)findViewById(R.id.image_upload);
        edt=(ImageView) findViewById(R.id.tv6);
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(UploadPresc.this,Addressadd.class);
                Bundle b = new Bundle();
                b.putString("name", mail4);
                it.putExtras(b);
                startActivity(it);
            }
        });
        upload=(ImageView)findViewById(R.id.image_upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

      /*  Bundle b = getIntent().getExtras();
        ddd=(String)b.getCharSequence("name");
        mail3=(String)b.getCharSequence("email");*/


        sk=(TextView)findViewById(R.id.tv_skip);
        sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(UploadPresc.this,Main2Activity.class);
                Bundle b = new Bundle();
                b.putString("name", aaa);
                it.putExtras(b);
                startActivity(it);

            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                upload.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(),"Prescription Uploaded  Successfully",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private class Logmem extends AsyncTask <String, Void, Boolean> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(UploadPresc.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("result");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        name1 = object.getString("first_name");
                        number1 = object.getString("mobileno");
                        address1 = object.getString("address");
                        city = object.getString("city");
                        state1=object.getString("state");
                        pincode=object.getString("pincode");

                    }
                    return true;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            name.setText(name1);
            email1.setText(mail4);
            num.setText(number1);
            address.setText(address1);
            city1.setText(city);
            pincode1.setText(pincode);
            if(result == false)
                Toast.makeText(getApplicationContext(),"Unable to fetch data from server",Toast.LENGTH_LONG).show();
        }
    }
}

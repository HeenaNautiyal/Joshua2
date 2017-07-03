package com.administrator.renudadlani;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 8/25/2016.
 */
public class ReplyList extends AppCompatActivity {
    ArrayList<Actors> actorsList;
    ActorAdapter1 adapter;
    Button btn_continue;
    String TAG = "MainActivity";
    String mail, lName, idname, adminid, adminmes, message, tokenbynotifi, email1, tokennew, select;
    EditText edmsg;
    CheckBox chbox;
    ImageButton btnview;
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb = "neha@outsourcingservicesusa.com";
    SharedPreferences settings, pwd;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        lName = it.getStringExtra("message");
        idname = it.getStringExtra("userid");
        new JSONAsyncTask().execute();
        actorsList = new ArrayList<Actors>();

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new ActorAdapter1(getApplicationContext(), R.layout.row1, actorsList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                         }
        });
    }

    class JSONAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adminid=idname;
            adminmes=lName;
            adminmes = adminmes.replaceAll(" ", "").trim();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://www.renudadlani.com/mobile_app/insertdata.php?case_id=5&email="+adminid+"&message="+adminmes+"";
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
                message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
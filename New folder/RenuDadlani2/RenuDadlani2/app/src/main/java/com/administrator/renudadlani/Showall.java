package com.administrator.renudadlani;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Showall extends AppCompatActivity {
    ArrayList<Actors> actorsList;
    ActorAdapter1 adapter;
    Button btn_continue;
    String TAG = "MainActivity";
    String mail, lName, idname, adminid, adminmes, num, tokenbynotifi, email1, tokennew, select;
    EditText edmsg;
    CheckBox chbox;
    ImageButton btnview;
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb = "neha@outsourcingservicesusa.com";
    SharedPreferences settings, pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showall);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        new Shodata().execute("http://www.renudadlani.com/mobile_app/insertdata.php?case_id=5&email=&message=");
        actorsList = new ArrayList<Actors>();

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new ActorAdapter1(getApplicationContext(), R.layout.row1, actorsList);
        listview.setAdapter(adapter);
        adapter.updateAdapter(actorsList);
        adapter.notifyDataSetChanged();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Showall.this);
                TextView myMsg = new TextView(Showall.this);
                myMsg.setText("Message!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                String ab=actorsList.get(position).getmessage();
                builder.setMessage(ab);
                builder.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                builder.show();
            }
        });
    }

    class Shodata extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                        Actors actor = new Actors();
                        actor.setemail(object.getString("email"));
                        actor.setmessage(object.getString("message"));
                        actorsList.add(actor);
                    }
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to connect from server", Toast.LENGTH_LONG).show();
        }
    }
}

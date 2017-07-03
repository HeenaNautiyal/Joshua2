package com.administrator.renudadlani;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Administrator on 8/25/2016.
 */
public class Notifyall extends AppCompatActivity {
    ArrayList<Actors> actorsList;
    ActorAdapter adapter;
    Uri URI = null;
    Button btn_continue;
    String TAG = "MainActivity";
    String mail, message1, email, adminid, token2, num, tokenbynotifi, email1, tokennew, select;
    EditText edmsg;
    CheckBox chbox;
    ImageButton btnview;
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    SharedPreferences settings, pwd;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        settings = getSharedPreferences(PREFS_NAME, 0);
        edmsg = (EditText) findViewById(R.id.message);
        chbox = (CheckBox) findViewById(R.id.chkAll2);

        chbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chbox.isChecked()) {
                    chekall();
                } else {
                    nochekall();
                }
            }
        });

        edmsg.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    btn_continue.setEnabled(false);
                } else {
                    btn_continue.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });
        actorsList = new ArrayList<Actors>();
        new JSONAsyncTask().execute("http://renudadlani.com/mobile_app/email.php?case_id=1");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ListView listview = (ListView) findViewById(R.id.list);
        btn_continue = (Button) findViewById(R.id.btn_send);

        adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);
        listview.setAdapter((ListAdapter) adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                Actors actors = actorsList.get(position);
                actors.setIsRowSelected(true);
                actorsList.set(position, actors);
                adapter.updateAdapter(actorsList);
                adapter.notifyDataSetChanged();
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Actors> countryList = adapter.actorList;
                for (int i = 0; i < countryList.size(); i++) {
                    Actors country = countryList.get(i);
                    if (country.isSelected()) {
                        mail = country.getDob();
                        num = country.getHeight();
                        token2 = country.getName();
                        new regisnotic().execute();
                        new sentNotice().execute();
                        edmsg.setText("");
                    }
                }
            }
        });
        btn_continue.setEnabled(false);
    }

    private void nochekall() {
        ArrayList<Actors> countryList = adapter.actorList;
        for (int i = 0; i < countryList.size(); i++) {
            Actors actors = countryList.get(i);
            {
                actors.isSelected(false);
            }
            adapter.updateAdapter(actorsList);
            adapter.notifyDataSetChanged();
        }
    }

    private void chekall() {
        ArrayList<Actors> countryList = adapter.actorList;
        for (int i = 0; i < countryList.size(); i++) {
            Actors actors = countryList.get(i);
            {
                actors.isSelected(true);
            }
            adapter.updateAdapter(actorsList);
            adapter.notifyDataSetChanged();
        }
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Notifyall.this);
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

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("result");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Actors actor = new Actors();
                        actor.setName(object.getString("id"));
                        actor.setDob(object.getString("email"));
                        actor.setCountry(object.getString("message"));
                        actor.setHeight(object.getString("token"));
                        actorsList.add(actor);
                    }
                    return true;
                }
                //------------------>>
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }

    private class sentNotice extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            email1 = token2;
            message1 = edmsg.getText().toString();
            message1 = message1.replaceAll(" ", "%20").trim();
            message1 = message1.replaceAll("\n","%20");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://renudadlani.com/mobile_app/gg.php?case_id=2&userid="+ email1+"&message="+message1+"&admin=&username=";
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
                String message = jsonResult.getString("success");
                Log.e("Success: ", message);
                if (message.equals("1")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Notifyall.this);
                    TextView myMsg = new TextView(Notifyall.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Your message has been sent.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            });
                    builder.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }


    private class regisnotic extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            email = num;
            tokennew = token2;

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://renudadlani.com/mobile_app/gg.php?case_id=1&userid=" + tokennew + "&regid=" + email + "&devicetype=android";
            String SetServerString = "";
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = httpClient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Response:  ", SetServerString);
            return SetServerString;
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Notifyall.this);
                    TextView myMsg = new TextView(Notifyall.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    //set custom title
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Please check the Mobile Data.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent it = new Intent(getApplicationContext(), Notifyall.class);
                                    startActivity(it);
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
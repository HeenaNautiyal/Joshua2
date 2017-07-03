package com.heena.testingfirebasecloud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Objects;

public class OTCCounter extends AppCompatActivity {

    ArrayList<Chemist> actorsList;
    ImageView btnback;
    ActorAdapter2 adapter;
    Button btn_continue;
    Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;
    SessionManager1 session;
    String TAG = "MainActivity";
    String abc, mobile, fname, address, medid, state, license, email, Epdate, mail, mail1,chemmail;
    TextView tv,medicine,composition,description,manufacture;
    private TextView mCounter;
    public int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otccounter);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        session = new SessionManager1(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        mail1 = user.get(SessionManager1.KEY_EMAIL);

        chemmail = user.get(SessionManager1.KEY_CHEMEMAIL);

        String status=  chemmail = user.get(SessionManager1.KEY_CHEMEMAIL);
        Log.d("status", status);

        btn_continue=(Button)findViewById(R.id.btn_continue);
        actorsList = new ArrayList<Chemist>();
        new JSONAsyncTask().execute("http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=27&catid=12");
        final ListView listview = (ListView) findViewById(R.id.list);
        btn_continue=(Button)findViewById(R.id.btn_continue);
        btnback = (ImageView) findViewById(R.id.back);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(OTCCounter.this,Main2Activity.class);
                startActivity(it);
            }
        });
        adapter = new ActorAdapter2(getApplicationContext(), R.layout.row2, actorsList);


        listview.setAdapter(adapter);
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Chemist actors = actorsList.get(position);
                actors.setIsRowSelected(true);
                actorsList.set(position, actors);
                adapter.updateAdapter(actorsList);
                adapter.notifyDataSetChanged();

                if (actors.getIsRowSelected()) {
                    buildDialog(R.style.DialogTheme, "Left - Right Animation!");
                }
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Chemist> countryList = adapter.actorList;
                for (int i = 0; i < countryList.size(); i++) {
                    Chemist actors = countryList.get(i);
                    if (actors.isSelected()) {
                        if (mCounter != null) {
                            count++; }
                        address=actors.getMedicine();
                        medid=actors.getMedicineid();
                        mobile = (Integer.toString(count));
                        selectionorder();
                    }
                }
            }
        });
    }

    private void buildDialog(int dialogTheme, String s) {
        LayoutInflater layoutInflater = LayoutInflater.from(OTCCounter.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog4, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OTCCounter.this);
        alertDialogBuilder.setView(promptView);
        AlertDialog alert = alertDialogBuilder.create();

        medicine = (TextView) promptView.findViewById(R.id.textView);
        composition = (TextView) promptView.findViewById(R.id.textView3);
        description = (TextView) promptView.findViewById(R.id.textView4);
        manufacture = (TextView) promptView.findViewById(R.id.textView2);

        alert.getWindow().getAttributes().windowAnimations = dialogTheme;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;
        wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position

        alert.show();
    }

    private void selectionorder() {
        Intent it = new Intent(OTCCounter.this,Ordernow.class);
        Bundle b = new Bundle();
        b.putString("medicine", address);
        b.putString("medid",medid);
        it.putExtras(b);
        startActivity(it);
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(OTCCounter.this);
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

                        Chemist actor = new Chemist();

                        actor.setMedicineid(object.getString("medicine_id"));
                        actor.setMedicine(object.getString("medicine_name"));
                        actor.setMenufacuter(object.getString("manufacture_by"));
                        actor.setCompostion(object.getString("composition"));
                        actor.setDescription(object.getString("description"));
                        actor.setPrice(object.getString("price"));
                        actor.setProductImage(object.getString("product_image"));
                        actor.setUserImage(object.getString("user_added_image"));
                        actor.setStock(object.getString("stock"));
                        actor.setCategory(object.getString("category_name"));
                        actorsList.add(actor);
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
            adapter.notifyDataSetChanged();
            if (result == false) {
                Toast.makeText(getApplicationContext(),"Unable to fetch data from server",Toast.LENGTH_LONG).show();
            }
        }

        public void onBackPressed() {
            moveTaskToBack(true);
        }
    }
}

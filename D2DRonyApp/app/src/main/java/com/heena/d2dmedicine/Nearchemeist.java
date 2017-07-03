package com.heena.d2dmedicine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.d2dronyapp.R;
import com.heena.d2dmedicine.Chemis;

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

public class Nearchemeist extends AppCompatActivity {
    ArrayList<Chemist> actorsList;
    ImageView btnback;
    ActorAdapter adapter;
    Button btn_continue;
    String TAG="MainActivity";
    String abc,name, fname,address,city,state;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearchemeist);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        Bundle b = getIntent().getExtras();
        abc= (String) b.getCharSequence("pincode");
        actorsList = new ArrayList<Chemist>();
        new JSONAsyncTask().execute("http://seajob.net/medicalapp/insertdata.php?caseid=8&pincode="+abc.replaceAll(" ","")+"");
        ListView listview = (ListView)findViewById(R.id.list);
        btn_continue=(Button)findViewById(R.id.btn_continue);
        btnback=(ImageView)findViewById(R.id.backbtn);
        tv=(TextView)findViewById(R.id.select);

        adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);

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
                for(Chemist actors1:actorsList) {
                    if (actors.getIsRowSelected()) {
                        Log.e(TAG, "selected Chemist: " + actors.getName());
                        fname = actors.getName();
                        tv.setText("Selected Chemist: "+fname.toString());
                    }
                }

            }
        });
        listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    btnback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent It =new Intent(Nearchemeist.this,Mem.class);
            startActivity(It);
        }
    });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"-----------------------------");
                for(Chemist actors:actorsList){
                    if(actors.getIsRowSelected()){
                        Log.e(TAG,"selected name: "+actors.getName());
                        fname=actors.getName();
                        address=actors.getAddress();
                        city=actors.getCity();
                        state=actors.getState();
                        Intent it= new Intent(Nearchemeist.this,Chemis.class);
                        Bundle b = new Bundle();
                        b.putString("name", fname);
                        b.putString("address",address);
                        b.putString("city",city);
                        b.putString("state",state);
                        it.putExtras(b);
                        startActivity(it);
                    }
                }
            }
        });
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Nearchemeist.this);
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

                        actor.setName(object.getString("fname"));
                        actor.setAddress(object.getString("address"));
                        actor.setCity(object.getString("city"));
                        actor.detState(object.getString("state"));
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
            if(result == false)
                Toast.makeText(getApplicationContext(),"Unable to fetch data from server",Toast.LENGTH_LONG).show();
        }
    }
}

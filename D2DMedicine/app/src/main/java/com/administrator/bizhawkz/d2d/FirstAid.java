package com.administrator.bizhawkz.d2d;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.administrator.d2d.R;

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

public class FirstAid extends AppCompatActivity {
    ArrayList<Chemist> actorsList;
    ActorAdapter2 adapter;
    String TAG="OTCClass";
    Button btn_continue;
    String Med,Menu,Comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        actorsList = new ArrayList<Chemist>();
        btn_continue=(Button)findViewById(R.id.btn_continue24);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        new JSONAsyncTask().execute("http://seajob.net/medicalapp/insertdata.php?caseid=13&cat_id=2");

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new ActorAdapter2(getApplicationContext(), R.layout.row4, actorsList);
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setSelector(R.color.blue);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Chemist actors = actorsList.get(position);
                actors.setIsRowSelected(true);
                actorsList.set(position, actors);
                adapter.updateAdapter(actorsList);
                adapter.notifyDataSetChanged();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"-----------------------------");
                for (Chemist actors : actorsList) {
                    if (actors.getIsRowSelected()) {
                        Log.e(TAG, "selected name: " + actors.getMenufacuter());
                        Log.e(TAG, "selected Image: " + actors.getMedicine());
                        Log.e(TAG, "selected Image: " + actors.getadded_by());
                        Med=actors.getMedicine();
                        Menu=actors.getMenufacuter();
                        Comp=actors.getadded_by();
                        Intent it = new Intent(FirstAid.this, NewDem.class);
                        it.putExtra("Medicine", Med);
                        it.putExtra("Menufacture", Menu);
                        it.putExtra("Added", Comp);
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
            dialog = new ProgressDialog(FirstAid.this);
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

                        Chemist actor = new Chemist();
                        actor.setCategory(object.getString("cat_id"));
                        actor.setMedicine(object.getString("medicine_name"));
                        actor.setMenufacuter(object.getString("manufacture_by"));
                        actor.setCompostion(object.getString("composition"));
                        actor.setAddedby(object.getString("added_by"));
                        actor.setTodaydate(object.getString("add_date"));
                        actor.setUserip(object.getString("ip"));
                        actor.setStock(object.getString("stock"));
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
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}

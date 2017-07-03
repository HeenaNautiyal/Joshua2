package com.heena.D2DMedical;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

public class OrderRecieve extends AppCompatActivity {
    ArrayList<Chemist> actorsList;
    ActorAdapter3 adapter;
    SessionManager1 session;
    String mail;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_recieve);
        actorsList = new ArrayList<Chemist>();

        session = new SessionManager1(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        mail = user.get(SessionManager1.KEY_EMAIL);
        iv=(ImageView)findViewById(R.id.imgback);


/*        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();*/

        new JSONAsyncTask().execute("http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=29&email="+mail+"");


        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new ActorAdapter3(getApplicationContext(), R.layout.row6, actorsList);
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
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(OrderRecieve.this,UploadMedicine.class);
                startActivity(it);
            }
        });
    }
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(OrderRecieve.this);
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
                        actor.setMedicine(object.getString("medicinename"));
                        actor.setMenufacuter(object.getString("manufacture"));
                        actor.setAddress(object.getString("address"));
                        actor.setdate(object.getString("date"));
                        actor.setQuantity(object.getString("quantity"));
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
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "No order has been recieved yet!", Toast.LENGTH_LONG).show();

        }

    }
}

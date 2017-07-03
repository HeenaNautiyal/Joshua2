package com.example.administrator.testing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Actors> actorsList;

    ActorAdapter adapter;
    Button btn_continue;
    String TAG = "MainActivity";
    String abd,f;
    int count;
    CheckBox chkall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actorsList = new ArrayList<Actors>();
        new JSONAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");

        ListView listview = (ListView) findViewById(R.id.list);
        btn_continue = (Button) findViewById(R.id.btn_continue);
        chkall=(CheckBox)findViewById(R.id.chkAll1);
        adapter = new ActorAdapter(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice,actorsList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                Actors actors = actorsList.get(position);
                actors.setIsRowSelected(true);
                actorsList.set(position, actors);
                adapter.updateAdapter(actorsList);
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "-----------------------------");
                StringBuffer responseText = new StringBuffer();
                List<String> dta= new ArrayList<>();
                    responseText.append("The following were selected...\n");
                    ArrayList<Actors> countryList = adapter.actorList;
                    for (int i = 0; i < countryList.size(); i++) {
                        Actors actors = countryList.get(i);
                        {
                            if (actors.isSelected()) {
                                abd = actors.getName();
                                f = actors.getHeight();
                                  Log.e(TAG, "selected responce: " + abd);
                            }

                        }
                    }
                    Toast.makeText(getApplicationContext(),
                            responseText, Toast.LENGTH_LONG).show();
                }



        });
        chkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList al = new ArrayList();
                Actors actor2 = actorsList.get(2);
                actor2.getName();
                actor2.getIsRowSelected();
                al.add(actor2);

                System.out.println("Contents of al: " + al);

                /*StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");
                ArrayList<Actors> countryList = adapter.actorList;

                for (int i = 0; i < countryList.size(); i++) {
                    Actors actors = countryList.get(i);
                    {
                            actors.isSelectedAll();
                            abd=actors.getName();
                            Log.e(TAG, "selected responce: " + abd);


                    }
                }
                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();*/
            }
        });




    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
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
                    JSONArray jarray = jsono.getJSONArray("actors");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Actors actor = new Actors();

                        actor.setName(object.getString("name"));
                        actor.setDescription(object.getString("description"));
                        actor.setDob(object.getString("dob"));
                        actor.setCountry(object.getString("country"));
                        actor.setHeight(object.getString("height"));
                        actor.setSpouse(object.getString("spouse"));
                        actor.setChildren(object.getString("children"));
                        actor.setImage(object.getString("image"));

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
}



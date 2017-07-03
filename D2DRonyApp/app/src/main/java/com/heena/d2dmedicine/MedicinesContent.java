package com.heena.d2dmedicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.heena.d2dmedicine.*;

import java.util.ArrayList;

public class MedicinesContent extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Button btn_FirstAid,btn_prescribed,btn_OTC,btn_Daily;
    String  adminfont;
    String aaa;
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    ArrayList<Chemist> actorsList;

    //ActorAdapter1 adapter;
    Button btn_continue;
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_content);

        Bundle b = getIntent().getExtras();
        aaa=(String)b.getCharSequence("name");

        settings = getSharedPreferences(PREFS_NAME, 0);
        btn_FirstAid=(Button)findViewById(R.id.btn_continue2);
        btn_prescribed=(Button)findViewById(R.id.btn_precmed);
        btn_OTC=(Button)findViewById(R.id.btn_continue23);
        btn_Daily=(Button)findViewById(R.id.btn_continue24);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (settings.getString("logged", "").toString().equals("logged")) {
            adminfont=settings.getString("email",null);
        }
        btn_OTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(MedicinesContent.this,OTCClass.class);
                startActivity(it);
            }
        });

        btn_FirstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(MedicinesContent.this,FirstAid.class);
                Bundle b = new Bundle();
                b.putString("name", adminfont);
                b.putString("storename",aaa);
                it.putExtras(b);
                startActivity(it);
            }
        });
        btn_Daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(MedicinesContent.this,DailyCare.class);
                Bundle b = new Bundle();
                b.putString("name", adminfont);
                it.putExtras(b);
                startActivity(it);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView !=null;
        View headerView =navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
       /* TextView txheader=(TextView)headerView .findViewById(R.id.tvlogin);
        txheader.setText(adminfont);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nevdemo_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


            if (id == R.id.iv_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now Learn Android with AndroidSolved click here to visit https://androidsolved.wordpress.com/ ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.iv_signout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MedicinesContent.this);
            TextView myMsg = new TextView(MedicinesContent.this);
            myMsg.setText("Warning!");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(20);
            myMsg.setTextColor(Color.BLACK);
            builder.setCustomTitle(myMsg).setMessage("Are you sure you wanna logout.")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.remove("logged");
                                    editor.remove("email");
                                    editor.commit();
                                    Intent it =new Intent(MedicinesContent.this,Login.class);
                                    startActivity(it);
                                }
                            })
                    .show();

        }
        else if (id == R.id.iv_myorder) {
              //  Toast.makeText(getApplicationContext(),"Page Under Construction",Toast.LENGTH_LONG).show();
            Intent it= new Intent(MedicinesContent.this,OrderTrack.class);
            startActivity(it);

        }
        else if (id == R.id.iv_profile) {
            Intent it= new Intent(MedicinesContent.this,Profile.class);
            startActivity(it);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(NevdemoContent.this);
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

                        Chemist actor = new Chemist();

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
           // adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }*/

    protected void onPause() {
        super.onPause();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("logged", "logged");
        editor.putString("email", "email");
        editor.commit();
    }
}

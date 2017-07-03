package com.heena.testingfirebasecloud;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Chemist> actorsList;
    ActorAdapter3 adapter;
    ImageView btn_FirstAid,btn_prescribed,btn_OTC,btn_Daily;
    String  adminfont;
    String im;
    SessionManager1 session;
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        settings = getSharedPreferences(PREFS_NAME, 0);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);
        //buildDialog(R.style.DialogTheme, "Left - Right Animation!");

        session = new SessionManager1(getApplicationContext());
        btn_FirstAid=(ImageView)findViewById(R.id.first);
        btn_FirstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(Main2Activity.this,FirstAid1.class);
                startActivity(it);
                /*Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();*/
            }
        });
btn_prescribed=(ImageView)findViewById(R.id.prescribe);
        btn_prescribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(Main2Activity.this,FirstAid1.class);
                startActivity(it);
            }
        });

        btn_OTC=(ImageView)findViewById(R.id.home);
        btn_OTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(Main2Activity.this,FirstAid1.class);
                startActivity(it);
            }
        });

        btn_Daily=(ImageView)findViewById(R.id.collage);
        btn_Daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(Main2Activity.this,FirstAid1.class);
                startActivity(it);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      /*  toolbar.setLogo(R.drawable.door);
        toolbar.setTitle("Toolbar");*/
        setSupportActionBar(toolbar);

        final ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Main2Activity.this);
                    Snackbar.make(view, "No internet connection", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void buildDialog(int dialogTheme, String s) {
        LayoutInflater layoutInflater = LayoutInflater.from(Main2Activity.this);
        final View promptView = layoutInflater.inflate(R.layout.activity_rating, null);
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Main2Activity.this);
        alertDialogBuilder.setView(promptView);


       /* ed = (EditText) promptView.findViewById(R.id.edittext);
        OK1 = (Button) promptView.findViewById(R.id.btn_ok);
        OK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().getAttributes().windowAnimations = dialogTheme;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;

        alert.show();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

        if (id == R.id.iv_profile) {
           Intent it = new Intent( Main2Activity.this,Profile.class);
            startActivity(it);

        } else if (id == R.id.iv_myorder) {
            Intent it= new Intent(Main2Activity.this,OrderTrack.class);
            startActivity(it);

        } else if (id == R.id.iv_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now Learn Android with AndroidSolved clicke here to visit https://androidsolved.wordpress.com/ ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.iv_signout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            TextView myMsg = new TextView(Main2Activity.this);
            myMsg.setText("Warning!");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(20);
            myMsg.setTextColor(Color.BLACK);
            builder.setCustomTitle(myMsg).setMessage("Are you sure you wanna logout.")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                         session.logoutUser();
                                }
                            })
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}

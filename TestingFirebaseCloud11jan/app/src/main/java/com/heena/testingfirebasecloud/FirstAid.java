package com.heena.testingfirebasecloud;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

public class FirstAid extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView list;

    String[] web = {
            "Dettol",
            "Hansaplast",
            "Paracitamol",
            "Glycerine",
            "Crocine",
            "Calamine Lotion",
            "Multivitamins"
    } ;
    Integer[] imageId = {
            R.drawable.bandaid,
            R.drawable.bandaid,
            R.drawable.tablet,
            R.drawable.liquid,
            R.drawable.tablet,
            R.drawable.liquid,
            R.drawable.tablet

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CustomList adapter = new
                CustomList(FirstAid.this, web, imageId);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent it = new Intent(FirstAid.this,Ordernow.class);
                Bundle b = new Bundle();
                b.putString("medicine", web[+position]);
                it.putExtras(b);
                startActivity(it);
                Toast.makeText(FirstAid.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_aid, menu);
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
            Intent it = new Intent( FirstAid.this,Profile.class);
            startActivity(it);

        } else if (id == R.id.iv_myorder) {

        } else if (id == R.id.iv_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now Learn Android with AndroidSolved clicke here to visit https://androidsolved.wordpress.com/ ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.iv_signout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FirstAid.this);
            TextView myMsg = new TextView(FirstAid.this);
            myMsg.setText("Warning!");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(20);
            myMsg.setTextColor(Color.BLACK);
            builder.setCustomTitle(myMsg).setMessage("Are you sure you wanna logout.")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    /*SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.remove("logged");
                                    editor.remove("username");
                                    editor.commit();*/
                                }
                            })
                    .show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

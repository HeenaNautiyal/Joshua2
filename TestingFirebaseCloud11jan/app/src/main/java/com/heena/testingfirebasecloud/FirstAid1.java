package com.heena.testingfirebasecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

public class FirstAid1 extends AppCompatActivity {
    ListView list;
    ImageView tv;

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
        setContentView(R.layout.activity_first_aid1);

        tv=(ImageView)findViewById(R.id.back);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(FirstAid1.this,Main2Activity.class);
                startActivity(it);
            }
        });

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        CustomList adapter = new
                CustomList(FirstAid1.this, web, imageId);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent it = new Intent(FirstAid1.this,Ordernow.class);
                Bundle b = new Bundle();
                b.putString("medicine", web[+position]);
                it.putExtras(b);
                startActivity(it);
                //Toast.makeText(FirstAid1.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

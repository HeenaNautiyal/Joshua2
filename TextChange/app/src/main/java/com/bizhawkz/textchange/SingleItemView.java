package com.bizhawkz.textchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Heena on 3/4/2017.
 */
public class SingleItemView extends Activity {
    // Declare Variables
    TextView txtrank;
    TextView txtcountry;
    TextView txtpopulation;
    String rank;
    String country;
    String population;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);
        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();
        // Get the results of rank
        rank = i.getStringExtra("rank");
        // Get the results of country
        country = i.getStringExtra("country");
        // Get the results of population
        population = i.getStringExtra("population");

        // Locate the TextViews in singleitemview.xml
        txtrank = (TextView) findViewById(R.id.rank);
        txtcountry = (TextView) findViewById(R.id.country);
        txtpopulation = (TextView) findViewById(R.id.population);

        // Load the results into the TextViews
        txtrank.setText(rank);
        txtcountry.setText(country);
        txtpopulation.setText(population);
    }
}
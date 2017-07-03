package com.heena.d2dmedicine;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Heena on 12/21/2016.
 */
public class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       /* Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

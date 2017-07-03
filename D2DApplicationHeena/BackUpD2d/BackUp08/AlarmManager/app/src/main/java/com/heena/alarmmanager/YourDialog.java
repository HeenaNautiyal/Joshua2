package com.heena.alarmmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Heena on 2/13/2017.
 */
public class YourDialog extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        Context ctx = this;

        final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setTitle("Message");
        alert.setIcon(R.drawable.alert2);
        alert.setMessage("checking");
        alert.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }});
        alert.create();
        alert.show();
    }

}

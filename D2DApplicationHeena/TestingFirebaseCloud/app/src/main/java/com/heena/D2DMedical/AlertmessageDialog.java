package com.heena.D2DMedical;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.heena.testingfirebasecloud.R;

/**
 * Created by Heena on 2/13/2017.
 */
public class AlertmessageDialog extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        Context ctx = this;

        final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setTitle("Alert");
        alert.setIcon(R.drawable.alert);
        alert.setMessage("Your medicine will be expire soon.\n Please update your Medicine.");
        alert.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }});
        alert.create();
        alert.show();
    }
}

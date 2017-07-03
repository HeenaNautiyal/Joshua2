package com.heena.testingfirebasecloud;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

/**
 * Created by Heena on 1/6/2017.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        /*buildDialog(R.style.DialogTheme, "Left - Right Animation!");

        Intent trIntent = new Intent("android.intent.action.MAIN");
        trIntent.setClass(context, com.heena.testingfirebasecloud.Ratingclass.class);
        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(trIntent);*/
        Intent trIntent = new Intent("android.intent.action.MAIN");
        trIntent.setClass(context, com.heena.testingfirebasecloud.Main2Activity.class);
        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(trIntent);
    }

   /* private void buildDialog(int dialogTheme, String s) {
        LayoutInflater layoutInflater = LayoutInflater.from(MyBroadcastReceiver.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Mem.this);
        alertDialogBuilder.setView(promptView);


        ed = (EditText) promptView.findViewById(R.id.edittext);
        OK1 = (Button) promptView.findViewById(R.id.btn_ok);
        OK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().getAttributes().windowAnimations = dialogTheme;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;

        alert.show();
    }*/
}

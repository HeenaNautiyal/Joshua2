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

        Intent trIntent = new Intent("android.intent.action.MAIN");
        trIntent.setClass(context, com.heena.testingfirebasecloud.Main2Activity.class);
        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(trIntent);
    }
}

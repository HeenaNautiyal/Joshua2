package com.heena.D2DMedical;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Heena on 1/6/2017.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Intent trIntent = new Intent("android.intent.action.MAIN");
        trIntent.setClass(context, AlertmessageDialog.class);
        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(trIntent);
    }
}

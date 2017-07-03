package com.heena.testingfirebasecloud;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.heena.testingfirebasecloud.R;

/**
 * Created by Heena on 1/7/2017.
 */
public class YourDialog extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        Context ctx = this;
        buildDialog(R.style.DialogTheme, "Left - Right Animation!");

    }

    private void buildDialog(int dialogTheme, String s) {
        LayoutInflater layoutInflater = LayoutInflater.from(YourDialog.this);
        final View promptView = layoutInflater.inflate(R.layout.activity_rating, null);
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(YourDialog.this);
        alertDialogBuilder.setView(promptView);

        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().getAttributes().windowAnimations = dialogTheme;
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alert.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM ;

        alert.show();
    }
}

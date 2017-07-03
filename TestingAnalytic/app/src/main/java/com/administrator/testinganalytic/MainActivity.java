package com.administrator.testinganalytic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;

    private Button btnSecondScreen, btnSendEvent, btnException, btnAppCrash, btnLoadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSecondScreen = (Button) findViewById(R.id.btnSecondScreen);
        btnSendEvent = (Button) findViewById(R.id.btnSendEvent);
        btnException = (Button) findViewById(R.id.btnException);
        btnAppCrash = (Button) findViewById(R.id.btnAppCrash);
        btnLoadFragment = (Button) findViewById(R.id.btnLoadFragment);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /**
         * Launching another activity to track the other screen
         */
        btnSecondScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}

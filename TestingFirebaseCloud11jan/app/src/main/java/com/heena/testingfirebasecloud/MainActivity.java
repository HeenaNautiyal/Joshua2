package com.heena.testingfirebasecloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tkn = FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(MainActivity.this, "Current token ["+tkn+"]",
                        Toast.LENGTH_LONG).show();
                Log.d("App", "Token ["+tkn+"]");
            }
        });
    }
}

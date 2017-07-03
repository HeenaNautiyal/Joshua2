package com.heena.d2dmedicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.d2dronyapp.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class Tokengen extends AppCompatActivity {
 Button b;
    TextView c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokengen);

        c=(TextView)findViewById(R.id.tv);

        b=(Button)findViewById(R.id.btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tkn = FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(Tokengen.this, "Current token ["+tkn+"]",
                        Toast.LENGTH_LONG).show();
                Log.d("App", "Token ["+tkn+"]");
               c.setText(tkn);


            }
        });
    }
}

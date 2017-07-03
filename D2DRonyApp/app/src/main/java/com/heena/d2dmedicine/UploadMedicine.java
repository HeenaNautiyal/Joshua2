package com.heena.d2dmedicine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.heena.d2dronyapp.R;

public class UploadMedicine extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_medicine);
        btn=(Button)findViewById(R.id.btn_upload);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(UploadMedicine.this, Chemistupload.class);
                startActivity(it);
            }
        });
    }


}

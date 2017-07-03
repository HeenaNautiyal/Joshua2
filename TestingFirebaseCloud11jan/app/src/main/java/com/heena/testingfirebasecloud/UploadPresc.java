package com.heena.testingfirebasecloud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

import java.io.IOException;

public class UploadPresc extends AppCompatActivity {
    ImageView upload;
    Button btnload;
    TextView sk;
    ImageView edt,presc;
    String aaa,ddd;
    SharedPreferences settings, pwd;
    public static final String PREFS_NAME = "LoginPrefs";
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_presc);
        settings = getSharedPreferences(PREFS_NAME, 0);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();


        presc=(ImageView)findViewById(R.id.image_upload);
        edt=(ImageView) findViewById(R.id.tv6);
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(UploadPresc.this,Addressadd.class);
                Bundle b = new Bundle();
                b.putString("name", aaa);
                it.putExtras(b);
                startActivity(it);
            }
        });
        upload=(ImageView)findViewById(R.id.image_upload);

        if (settings.getString("logged", "").toString().equals("logged")) {
            aaa=settings.getString("username","");
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        Bundle b = getIntent().getExtras();
        ddd=(String)b.getCharSequence("name");

        sk=(TextView)findViewById(R.id.tv_skip);
        sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(UploadPresc.this,Main2Activity.class);
                Bundle b = new Bundle();
                b.putString("name", aaa);
                it.putExtras(b);
                startActivity(it);

            }
        });
           FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_continue2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Toast.makeText(getApplicationContext(),"Your  Prescription has been uploaded.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                upload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

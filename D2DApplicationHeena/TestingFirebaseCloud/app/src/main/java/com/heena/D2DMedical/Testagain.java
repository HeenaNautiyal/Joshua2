package com.heena.D2DMedical;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.heena.testingfirebasecloud.R;

import java.io.File;
import java.io.FileNotFoundException;

public class Testagain extends AppCompatActivity {
   ImageView btnimage;
    String image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testagain);

        Bundle b = getIntent().getExtras();
        image1 = (String) b.getCharSequence("img");
        btnimage=(ImageView)findViewById(R.id.imgView);
        Uri uriFromPath = Uri.fromFile(new File(image1));

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        btnimage.setImageBitmap(bitmap);
    }
}

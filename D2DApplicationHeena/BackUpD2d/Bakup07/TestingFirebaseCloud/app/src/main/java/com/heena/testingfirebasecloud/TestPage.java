package com.heena.testingfirebasecloud;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.heena.testingfirebasecloud.R;

import java.io.File;
import java.io.FileNotFoundException;

public class TestPage extends AppCompatActivity {
    ImageView imageView;
    String realPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);
        imageView = (ImageView) findViewById(R.id.imgView);
        Bundle b = getIntent().getExtras();
        realPath = (String) b.getCharSequence("image");

        Uri uriFromPath = Uri.fromFile(new File(realPath));

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);

    }
}

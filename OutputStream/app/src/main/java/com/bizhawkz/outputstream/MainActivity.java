package com.bizhawkz.outputstream;

import android.app.ActionBar;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
Button btn;
    String filePath,v;
    SimpleDateFormat formatter;
    File file,file1;
    static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  ActionBar actionBar = getActionBar();
        actionBar.show();*/

        // Locate ImageView in activity_main.xml

        Date today = Calendar.getInstance().getTime();
        formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");

        // (3) create a new String using the date format we want
    //    String folderName = formatter.format(today);

        // (4) this prints "Folder Name = 2009-09-06-08.23.23"

        btn=(Button)findViewById(R.id.btnSelect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBrowse();
            }
        });
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                Uri picUri = data.getData();

                filePath = getPath(picUri);

                Log.d("picUri", picUri.toString());
                 file1=new File(filePath);
            }

        }

    }

    private String getPath(Uri picUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), picUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Create an actionbar menu
        menu.add("Save Image")
                // Add a new Menu Button
                .setOnMenuItemClickListener(this.SaveImageClickListener)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }

    // Capture actionbar menu item click
    MenuItem.OnMenuItemClickListener SaveImageClickListener = new MenuItem.OnMenuItemClickListener() {

        public boolean onMenuItemClick(MenuItem item) {


            storeOnLocation(file1);
                return false;
        }
    };

    private String storeOnLocation(File file) {

        File parentFile = new File("http://d2dmedicine.com/aPPmob_lie/chemist/");

        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        Log.d("store file ::" , file.getName());

        File fileToStore = new File(parentFile, file.getName().replace(" ", "_"));
        if (!fileToStore.exists()) {
            try {
                fileToStore.createNewFile();
            } catch (IOException e) {
                Log.e("error " + e.getMessage(), e.getMessage());
                // e.printStackTrace();
            }
        } else {
            fileToStore = new File(parentFile, file.getName().replace(" ", "_").replaceAll("\\.", formatter.format(Calendar.getInstance().getTime()) + "."));

            if (!fileToStore.exists()) {
                try {
                    fileToStore.createNewFile();

                } catch (IOException e) {
                    Log.e("error " + e.getMessage(), e.getMessage());
                }
            }
        }
        Log.d("FilePath", String.valueOf(fileToStore));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileToStore);
            byte[] data = getBytesFromFile(file);
            fos.write(data);
        } catch (Exception e) {

        }/* finally {
           *//* try {
               //  fos.flush();
                fos.close();
            } catch (IOException e) {
                Log.e("error " + e.getMessage(), e.getMessage());
            }*//*

        }*/

        return fileToStore.getName();

    }

    private byte[] getBytesFromFile(File file){
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

}

package com.heena.testingmuiltipart;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
        import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
        import org.apache.http.entity.mime.content.ByteArrayBody;
        import org.apache.http.entity.mime.content.StringBody;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.protocol.BasicHttpContext;
        import org.apache.http.protocol.HttpContext;
        import org.apache.http.util.EntityUtils;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.Bitmap.CompressFormat;
        import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
        import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=10";
    Button camera;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera= (Button) findViewById(R.id.btncamera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                callToUpload();
            }
        });
    }
    public void callToUpload(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, 1);

    }

    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    Log.d("bitmat", "" + bitmap);
                    new UploadImage().execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class UploadImage extends AsyncTask<String, String,String> {
        StringBuilder s = new StringBuilder();


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub            super.onPreExecute();


        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                byte[] data = bos.toByteArray();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(
                        URL);
                ByteArrayBody bab = new ByteArrayBody(data, "image1.jpg");

                MultipartEntity reqEntity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                String a="heena@outsourcingservicesusa.com";

                reqEntity.addPart("image", bab);

                postRequest.setEntity(reqEntity);
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));
                String sResponse;


                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                // Log.e("Response", s.toString());            } catch (Exception e) {
                // handle exception here                //  Log.e(e.getClass().getName(), e.getMessage());            }

                return s.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s.toString();
        }

            @Override protected void onPostExecute (String result){
                // TODO Auto-generated method stub            super.onPostExecute(result);
                if (result != null) {
                }

            }
        }
    }


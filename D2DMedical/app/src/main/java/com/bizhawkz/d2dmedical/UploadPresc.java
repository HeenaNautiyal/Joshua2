package com.bizhawkz.d2dmedical;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadPresc extends AppCompatActivity {
    ImageView upload,im;
    Button btnload,btnpres;
    TextView sk,email1,num,address,city1,pincode1,name;
    ImageView edt;
    String aaa,ddd,mail,name1,number1,address1,city,state1,pincode,mail3,mail4;
    SharedPreferences settings, pwd;
    ArrayList<Chemist> actorsList;

    ActorAdapter adapter;
    SessionManager1 session;
    public static final String PREFS_NAME = "LoginPrefs";
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    ProgressDialog pb;
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_presc);
        btnload =(Button)findViewById(R.id.btn_continue);
        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(UploadPresc.this,FirstAid1.class);
                Bundle b= new Bundle();
                b.putString("imageapth",ddd);
                it.putExtras(b);
                startActivity(it);
            }
        });
        btnpres=(Button)findViewById(R.id.image_upload);
        btnpres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 2. pick image only
                intent.setType("image/*");
                // 3. start activity
                startActivityForResult(intent, 0);
            }
        });
//        session.createImage(ddd.replaceAll(" ", ""));
        pb = new ProgressDialog(UploadPresc.this);

        session = new SessionManager1(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        mail4 = user.get(SessionManager1.KEY_EMAIL);

        name=(TextView)findViewById(R.id.textView3);
        email1=(TextView) findViewById(R.id.mail);
        num=(TextView)findViewById(R.id.number);
        address=(TextView)findViewById(R.id.address);
        city1=(TextView)findViewById(R.id.city);
        pincode1=(TextView)findViewById(R.id.pincode);

        new Logmem().execute("http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=19&email="+mail4+"");

        edt=(ImageView) findViewById(R.id.tv6);
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(UploadPresc.this,Addressadd.class);
                Bundle b = new Bundle();
                b.putString("name", mail4);
                it.putExtras(b);
                startActivity(it);
            }
        });
        upload=(ImageView)findViewById(R.id.image_upload1);

        sk=(TextView)findViewById(R.id.tv_skip);
        sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(UploadPresc.this,MainActivity3.class);
                Bundle b = new Bundle();
                b.putString("name", aaa);
                b.putString("imageapth",ddd);
                it.putExtras(b);
                startActivity(it);

            }
        });

    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if(resCode == Activity.RESULT_OK && data != null){
            String realPath;
            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());


            setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(),realPath);
        }
    }

    private void setTextViews(int sdk, String uriPath,String realPath){


        Uri uriFromPath = Uri.fromFile(new File(realPath));
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        upload.setImageBitmap(getCircleBitmap(bitmap));

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:"+sdk);
        Log.d("HMKCODE", "URI Path:"+uriPath);
        Log.d("HMKCODE", "Real Path: "+realPath);
        ddd=realPath;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    private class Logmem extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(UploadPresc.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);

                    JSONArray jarray = jsono.getJSONArray("result");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        name1 = object.getString("first_name");
                        number1 = object.getString("mobileno");
                        address1 = object.getString("address");
                        city = object.getString("city");
                        state1 = object.getString("state");
                        pincode = object.getString("pincode");

                    }
                    return true;

                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result)  {
            dialog.cancel();
            name.setText(name1);
            email1.setText(mail4);
            num.setText(number1);
            address.setText(address1);
            city1.setText(city);
            pincode1.setText(pincode);
            if(result == false){
                final AlertDialog.Builder builder = new AlertDialog.Builder(UploadPresc.this);
                TextView myMsg = new TextView(UploadPresc.this);
                myMsg.setText("Alert!");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Please login to proceed further.");
                builder.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                session.logoutUser();
                            }
                        });
                builder.show();
            }

        }
    }
}

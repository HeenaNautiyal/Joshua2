package com.heena.testingfirebasecloud;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chemistupload extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn,btnupload;
    ImageView ivback;
    private RadioGroup radioGroup;
    private RadioButton tab, liq, inj, choi;
    EditText cat, subcat, med, manufactur, comp, desc,
            addedby, stock;
    String ipAddress, mail, imageString, encodedImage, cat1, subcart1,
            encImage, med1, manu1, compo1, des1, addedby1, date1, stock1, name, item;
    TextView addeddate, addmail;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;
    SessionManager1 session;
    Spinner spinner;
    //Button btnload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistupload);
        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        btn = (Button) findViewById(R.id.btn_Submit);
        btnupload=(Button)findViewById(R.id.btn_continue1);


        ivback = (ImageView) findViewById(R.id.imgback);
        session = new SessionManager1(getApplicationContext());

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();

        spinner = (Spinner) findViewById(R.id.ef_catid);
        spinner.setOnItemSelectedListener(this);

        HashMap<String, String> user = session.getUserDetails();
        mail = user.get(SessionManager1.KEY_EMAIL);

        addmail = (TextView) findViewById(R.id.ed_add);
        addmail.setText(mail);
        final Spinner spinner = (Spinner) findViewById(R.id.ef_catid);
        String[] plants = new String[]{
                "Please click for options",
                "OTC",
                "Prescribed",
                "Baby Care",
                "First Aid",
                "Daily Care"
        };

        List<String> categories = new ArrayList<String>();
        categories.add("Please click for options");
        categories.add("OTC");
        categories.add("Prescribed");
        categories.add("Baby Care");
        categories.add("First Aid");
        categories.add("Daily Care");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories)
        /*final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                this,R.id.ef_catid,categories)*/ {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        // cat=(EditText)findViewById(R.id.ef_catid);
        subcat = (EditText) findViewById(R.id.ef_subcat);
        med = (EditText) findViewById(R.id.ed_Medicine);
        manufactur = (EditText) findViewById(R.id.ed_manufactur);
        comp = (EditText) findViewById(R.id.ed_comp);
        desc = (EditText) findViewById(R.id.ed_desc);
        addeddate = (TextView) findViewById(R.id.ed_Date);
        stock = (EditText) findViewById(R.id.ed_stock);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Chemistupload.this, UploadMedicine.class);
                startActivity(it);
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        display(strDate);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_tab) {
                    // name="Tablet";
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tablet);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


                } else if (checkedId == R.id.rb_liquid) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.liquid);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                   /* name="Liquid";*/
                } else if (checkedId == R.id.rb_injec) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.injection);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                /*   name="Injection";*/
                } else {
                    //showFileChooser();
                    // name="YourChoice";
                }

            }

        });

        tab = (RadioButton) findViewById(R.id.rb_tab);
        liq = (RadioButton) findViewById(R.id.rb_liquid);
        inj = (RadioButton) findViewById(R.id.rb_injec);
        choi = (RadioButton) findViewById(R.id.rb_your);
        /*btn = (Button) findViewById(R.id.btn_continue);
        btn.setOnClickListener(this);*/
        /* btn.setOnClickListener((View.OnClickListener) this);*/
       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.equals("OTC")){
                    cat1="12";
                }else if(item.equals("Prescribed")){
                    cat1="11";
                }else if(item.equals("Baby Care")){
                    cat1="14";
                }else if(item.equals("First Aid")){
                    cat1="15";
                }else  if(item.equals("Daily Care")) {
                    cat1 = "16";
                }

                med1 = med.getText().toString();
                manu1 = manufactur.getText().toString();
                compo1 = comp.getText().toString();
                des1 = desc.getText().toString();
                date1 = addeddate.getText().toString();
                stock1 = stock.getText().toString();


              //  loginUser(cat1,"subcategoryname",med1.replaceAll(" ",""),manu1.replaceAll(" ",""),compo1.replaceAll(" ",""),des1.replaceAll(" ",""),"",imageString,subcart1,mail,date1,ipAddress,stock1.replaceAll(" ",""));
            }
        });*/
    }

    private void loginUser(String cat, String subname, String med, String menu, String comp, String desc, String price, String im1, String im2, String addby, String adddate, String ip, String stk) {

        Call<ResponseBody> callback = Network.getBaseInstance().loginUser(Constants1.CASE_ID, cat, subname, med, menu, comp, desc, price, im1, im2, addby, adddate, ip, stk);
        callback.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    Log.e("ChemistUpload", "my response" + res);
                    //check status vagara all thing
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ChemistUpload", t.toString());
            }
        });
    }


    private void display(String strDate) {
        TextView textView = (TextView) findViewById(R.id.ed_Date);
        textView.setText(strDate);
    }

    private void showFileChooser() {
       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // 2. pick image only
        intent.setType("image*//*");
        // 3. start activity
        startActivityForResult(intent, 0);*/
    }

    @Override
    protected void onActivityResult(int resCode, int resultCode, Intent data) {
        if (resCode == Activity.RESULT_OK && data != null) {
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
                setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(), realPath);
        }

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 2. pick image only
                intent.setType("image/*");
                // 3. start activity
                startActivityForResult(intent, 0);

            }
        });
    }

    private void setTextViews(int sdk, String uriPath, String realPath) {


        Uri uriFromPath = Uri.fromFile(new File(realPath));

        Bitmap bitmap = null;
       /* try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);*/

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:"+sdk);
        Log.d("HMKCODE", "URI Path:"+uriPath);
        Log.d("HMKCODE", "Real Path: "+realPath);
        med1=realPath;
    }


   /* @Override
    public void onClick(View v) {
        Intent it = new Intent(Chemistupload.this, TestPage.class);
        Bundle b = new Bundle();
        b.putString("image", med1);
        it.putExtras(b);
        startActivity(it);
    }*/

    private String encodeImage(Bitmap selectedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        encImage = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("Uploaded Image", encImage);
        return encImage;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
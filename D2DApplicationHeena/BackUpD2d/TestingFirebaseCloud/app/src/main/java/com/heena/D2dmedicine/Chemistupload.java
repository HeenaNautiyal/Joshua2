package com.heena.D2dmedicine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heena.testingfirebasecloud.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Chemistupload extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button btn;
    ImageView ivback;
    private RadioGroup radioGroup;
    private RadioButton tab, liq, inj,choi;
    EditText cat,subcat,med,manufactur,comp,desc,
          addedby,stock;
    String ipAddress,mail,interesting,encodedImage, cat1,subcart1,
            encImage,med1,manu1,compo1,des1,addedby1,date1,stock1,name,item;
    TextView addeddate;
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
        btn=(Button) findViewById(R.id.btn_Submit);
        ivback=(ImageView)findViewById(R.id.imgback);
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

        final Spinner spinner = (Spinner) findViewById(R.id.ef_catid);
        String[] plants = new String[]{
                "OTC",
                "Prescribed",
                "Baby Care",
                "First Aid",
                "Daily Care"
        };

        List<String> categories = new ArrayList<String>();
        categories.add("OTC");
        categories.add("Prescribed");
        categories.add("Baby Care");
        categories.add("First Aid");
        categories.add("Daily Care");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

       // cat=(EditText)findViewById(R.id.ef_catid);
        subcat=(EditText)findViewById(R.id.ef_subcat);
        med=(EditText)findViewById(R.id.ed_Medicine);
        manufactur=(EditText)findViewById(R.id.ed_manufactur);
        comp=(EditText)findViewById(R.id.ed_comp);
        desc=(EditText)findViewById(R.id.ed_desc);
        addedby=(EditText)findViewById(R.id.ed_add);
        addeddate=(TextView)findViewById(R.id.ed_Date);
        stock=(EditText)findViewById(R.id.ed_stock);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Chemistupload.this,UploadMedicine.class);
                startActivity(it);
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate =  mdformat.format(calendar.getTime());
        display(strDate);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rb_tab) {
                    name="Tablet";
                    /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tablet);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                     imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/


                } else if(checkedId == R.id.rb_liquid) {
                   /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.liquid);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                     imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/
                    name="Liquid";
                } else if(checkedId == R.id.rb_injec) {
                    /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.injection);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                     imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/
                    name="Injection";
                }
                else{
                    /*showFileChooser();*/
                    name="YourChoice";
                }

            }

        });

        tab = (RadioButton) findViewById(R.id.rb_tab);
        liq = (RadioButton) findViewById(R.id.rb_liquid);
        inj = (RadioButton) findViewById(R.id.rb_injec);
        choi= (RadioButton)findViewById(R.id.rb_your);
        btn=(Button)findViewById(R.id.btn_continue);

        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                new Uploading().execute();
             /*   if(item.equals("OTC")){
                    cat1="12";
                }else if(item.equals("Prescribed")){
                    cat1="11";
                }else if(item.equals("Baby Care")){
                    cat1="14";
                }else if(item.equals("First Aid")){
                    cat1="15";
                }else  if(item.equals("Daily Care")) {
                    cat1 = "16";
                }*/
                //Toast.makeText(getApplicationContext(),item+cat1,Toast.LENGTH_SHORT).show();
            }
                //cat1=cat.getText().toString();
                /*subcart1=subcat.getText().toString();
                med1=med.getText().toString();
                manu1=manufactur.getText().toString();
                compo1=comp.getText().toString();
                des1=desc.getText().toString();
                addedby1=addedby.getText().toString();
                date1=addeddate.getText().toString();
                stock1=stock.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId == tab.getId()) {
                } else if(selectedId == liq.getId()) {

                } else if(selectedId == inj.getId()){
                }
                else
                { }
                new Uploading().execute();
            }*/
        });
    }

    private void display(String strDate) {
        TextView textView = (TextView) findViewById(R.id.ed_Date);
        textView.setText(strDate);
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
             encodedImage = encodeImage(selectedImage);
        }
    }

    private String encodeImage(Bitmap selectedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
         encImage = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("Uploaded Image",encImage);
        return encImage;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class Uploading extends AsyncTask<String, String, String> {
        protected void onPreExecute() {

           // cat1 = cat.getText().toString();
            subcart1 = subcat.getText().toString();
            med1 = med.getText().toString();
            manu1 = manufactur.getText().toString();
            compo1 = comp.getText().toString();
            des1 = desc.getText().toString();
            addedby1 = addedby.getText().toString();
            date1 = addeddate.getText().toString();
            stock1 = stock.getText().toString();
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
        }

        @Override
        protected String doInBackground(String... strings) {
          /*  HttpClient httpClient = new DefaultHttpClient();
            String url ="http://outsourcingservicesusa.com/d2d/insertdata.php?%20caseid=12&cat_id="+cat1.replaceAll(" ", "")+"&sub_cat="+subcart1.replaceAll(" ", "")+"&medicine_name="+med1.replaceAll(" ", "")+"&manufacture_by="+manu1.replaceAll(" ", "")+"&compositi%20on="+compo1.replaceAll(" ", "")+"&description="+des1.replaceAll(" ", "")+"&price=&product_image="+imageString.replaceAll(" ","")+"&product_custome="+encImage.replaceAll(" ","")+"&added_by="+mail+"&add_date="+addedby1+"&ip="+ipAddress+"&stock="+stock1.replaceAll(" ","")+"";
            String SetServerString = "";
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = httpClient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + SetServerString);
            return SetServerString;*/
            HttpClient httpClient = new DefaultHttpClient();
            String url = "http://outsourcingservicesusa.com/d2d/insertdata.php?%20caseid=12&cat_id="+cat1.replaceAll(" ","")+"&sub_cat="+subcart1.replaceAll(" ","")+"&medicine_name="+med1.replaceAll(" ","")+"&manufacture_by="+manu1.replaceAll(" ","")+"&compositi%20on="+compo1.replaceAll(" ","")+"&description="+des1.replaceAll(" ","")+"&price=&product_image="+name.replaceAll(" ","")+"&product_custome="+name.replaceAll(" ","")+"&added_by="+mail.replaceAll(" ","")+"&add_date="+date1.replaceAll(" ","")+"&ip="+ipAddress+"&stock="+stock1.replaceAll(" ","")+"";
            String SetServerString = "";
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                SetServerString = httpClient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Response: ", "> " + SetServerString);
            return SetServerString;

        }

        protected void onPostExecute(String result) {
            try {

                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    Toast.makeText(getApplicationContext(), "Medicine Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

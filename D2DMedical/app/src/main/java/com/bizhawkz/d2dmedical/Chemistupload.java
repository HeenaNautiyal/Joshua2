package com.bizhawkz.d2dmedical;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Chemistupload extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn,btnupload;
    ImageView ivback;
    ProgressDialog pb;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistupload);
        pb = new ProgressDialog(Chemistupload.this);
        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        btn = (Button) findViewById(R.id.btn_continue);
        btnupload=(Button)findViewById(R.id.upload);
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

        ivback = (ImageView) findViewById(R.id.imgback);
        session = new SessionManager1(getApplicationContext());

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

       /* getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().hide();*/

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
        {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
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
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
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
                    name="Tablet";
                } else if (checkedId == R.id.rb_liquid) {
                    name="Liquid";
                } else if (checkedId == R.id.rb_injec) {
                    name="Injection";
                }
                else if(checkedId == R.id.rb_injec)
                {
                    Toast.makeText(getApplicationContext(),"Please click the button for uploading image",Toast.LENGTH_LONG).show();
                }
            }

        });
        btn.setOnClickListener(new View.OnClickListener() {
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

                tab = (RadioButton) findViewById(R.id.rb_tab);
                liq = (RadioButton) findViewById(R.id.rb_liquid);
                inj = (RadioButton) findViewById(R.id.rb_injec);
                choi= (RadioButton)findViewById(R.id.rb_your);

                med1 = med.getText().toString();
                manu1 = manufactur.getText().toString();
                compo1 = comp.getText().toString();
                des1 = desc.getText().toString();
                date1 = addeddate.getText().toString();
                stock1 = stock.getText().toString();

                if (cat1.matches("") || med1.matches("") || manu1.matches("") ||
                        compo1.matches("") || des1.matches("") || mail.matches("")
                        || date1.matches("") ||  stock1.matches(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chemistupload.this);
                    TextView myMsg = new TextView(Chemistupload.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);

                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("All fields are mandatory.");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();

                }else {
                    new uploadmed().execute();
                }
            }
        });
    }



    private void display(String strDate) {
        TextView textView = (TextView) findViewById(R.id.ed_Date);
        textView.setText(strDate);
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
        Log.d("HMKCODE", "Build.VERSION.SDK_INT:"+sdk);
        Log.d("HMKCODE", "URI Path:"+uriPath);
        Log.d("HMKCODE", "Real Path: "+realPath);
        imageString=realPath;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private class uploadmed extends AsyncTask<String, String, String> {
        ProgressDialog dialog;
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Chemistupload.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            String url="http://d2dmedicine.com/aPPmob_lie/insertdata.php?caseid=12&cat_id="+cat1+"" +
                    "&sub_cat=subcategoryname&medicine_name="+med1.replaceAll(" ","")+"" +
                    "&manufacture_by="+manu1.replaceAll(" ","")+"&compositi%20on="+compo1.replaceAll(" ","")+"" +
                    "&description="+des1.replaceAll(" ","")+"&price=" +
                    "&product_image="+name+"&product_custome="+imageString.replaceAll(" ","")+"" +
                    "&added_by="+mail+"&add_date="+date1.replaceAll(" ","")+"" +
                    "&ip="+ipAddress+"&stock="+stock1+"";
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
            dialog.cancel();
            try {
                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chemistupload.this);
                    TextView myMsg = new TextView(Chemistupload.this);
                    myMsg.setText("Medicine Uploaded");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Your medicine has been uploaded");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    med.setText("");
                                    manufactur.setText("");
                                    comp.setText("");
                                    desc.setText("");
                                    stock.setText("");
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
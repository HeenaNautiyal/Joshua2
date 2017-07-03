package com.administrator.bizhawkz.d2d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.administrator.d2d.R;

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
               /* catid1 = catid.getText().toString();
                name = medname.getText().toString();
                manu = manuby.getText().toString();
                comp1 = comp.getText().toString();
                add = addedby.getText().toString();
                date = currentdate.getText().toString();
                stock1 = stock.getText().toString();
                new Upload().execute("http://seajob.net/medicalapp/insertdata.php?caseid=12&cat_id=" + catid1.replaceAll(" ", "") + "\n" +
                        "&med_name=" + name.replaceAll(" ", "") + "&manu_by=" + manu.replaceAll(" ", "") + "" +
                        "&comps=" + comp1.replaceAll(" ", "") + "" +
                        "&added_by=" + add.replaceAll(" ", "") + "&today_date=12/15/2016&user_ip=" + ip + "&stock=" + stock1 + "");

            }
        });
        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new ActorAdapter2(getApplicationContext(), R.layout.row4, actorsList);
        listview.setAdapter(adapter);*/




   /* private void NetwordDetect() {
        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

                if (netInfo.isConnected())

                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

                if (netInfo.isConnected())

                    MOBILE = true;
        }

        if (WIFI == true)

        {
            ip = GetDeviceipWiFiData();
            ip1 = ip;
            userip.setText(ip);


        }

        if (MOBILE == true) {

            ip = GetDeviceipMobileData();
            ip1 = ip;
            userip.setText(ip);

        }
    }

    private String GetDeviceipMobileData() {

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

        @SuppressWarnings("deprecation")

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }

    private String GetDeviceipWiFiData() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }


  *//*  private class Upload extends AsyncTask<String, Void, Boolean> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMessage("Please wait while Loading...");
            pb.show();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);
                    String message = jsono.getString("udata");
                    if (message.equals("1")) {
                        message1 = "Success";
                        JSONArray jarray = jsono.getJSONArray("result");

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject object = jarray.getJSONObject(i);
                            Chemist actor = new Chemist();

                            actor.setCategory(object.getString("cat_id"));
                            actor.setMedicine(object.getString("medicine_name"));
                            actor.setMenufacuter(object.getString("manufacture_by"));
                            actor.setCompostion(object.getString("composition"));
                            actor.setAddedby(object.getString("added_by"));
                            actor.setTodaydate(object.getString("add_date"));
                            actor.setUserip(object.getString("ip"));
                            actor.setStock(object.getString("stock"));

                        }

                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(String result) {
            try {

                JSONObject jsonResult = new JSONObject(result);
                String message = jsonResult.getString("udata");
                Log.d("Response: ", "> " + message);
                if (message.equals("1")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(com.administrator.d2dmedicine.UploadMedicine.this);
                    TextView myMsg = new TextView(com.administrator.d2dmedicine.UploadMedicine.this);
                    myMsg.setText("Congratulations!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("You have successfully added the Medicine.");
                    builder.setPositiveButton("Continue.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    catid1 = catid.getText().toString();
                                    name = medname.getText().toString();
                                    manu = manuby.getText().toString();
                                    comp1 = comp.getText().toString();
                                    add = addedby.getText().toString();
                                    date = currentdate.getText().toString();
                                    stock1 = stock.getText().toString();
                                    catid.setText("");
                                    medname.setText("");
                                    manuby.setText("");
                                    comp.setText("");
                                    addedby.setText("");
                                    currentdate.setText("");
                                    stock.setText("");
                                   Intent it = new Intent(UploadMedicine.this, SearchChemist.class);
                                    startActivity(it);

                                }
                            });
                    builder.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UploadMedicine.this);
                    TextView myMsg = new TextView(UploadMedicine.this);
                    myMsg.setText("Warning!");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    myMsg.setTextColor(Color.BLACK);
                    builder.setCustomTitle(myMsg);
                    builder.setMessage("Medicine is not upload.");
                    builder.setPositiveButton("OK.",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
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
        });
    }*/


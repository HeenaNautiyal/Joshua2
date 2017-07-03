package com.administrator.bizhawkz.d2d;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.administrator.d2d.R;

public class NewDem extends AppCompatActivity {
     ImageView image,image2;
    String venName,email,subject,message,Manufacture,Added;
    Uri URI = null;
    TextView namemed,comp,added;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dem);

        Bundle extras = getIntent().getExtras();
        image2=(ImageView)findViewById(R.id.im12);
        namemed=(TextView)findViewById(R.id.tvmain);
        comp=(TextView)findViewById(R.id.tv_comp);
        //added=(TextView)findViewById(R.id.tv_add);

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sendmail();
            }
        });

        venName = extras.getString("Medicine");
        Manufacture=extras.getString("Menufacture");
        Added=extras.getString("Added");

        namemed.setText(venName);
        comp.setText(Manufacture);
      //  added.setText(Added);

    }

    private void Sendmail() {
        email = "heena@outsourcingservices.com";
        subject ="Order for medicine";
        message= "you have order for the Specific medicine";
        final Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                subject);
        if (URI != null) {
            emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
        }
        emailIntent
                .putExtra(android.content.Intent.EXTRA_TEXT, message);
        this.startActivity(Intent.createChooser(emailIntent,
                "Sending email..."));
    }

   /* private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
*/
        // Creates Bitmap from InputStream and returns it
       /* private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }*/
    }

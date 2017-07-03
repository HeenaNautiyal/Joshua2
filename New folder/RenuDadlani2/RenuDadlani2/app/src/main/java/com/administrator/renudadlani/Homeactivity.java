package com.administrator.renudadlani;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Homeactivity extends AppCompatActivity {
    TextView tv;
    WebView webView;
    ProgressBar progressBar;
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb="neha@outsourcingservicesusa.com";
    SharedPreferences settings,pwd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        settings = getSharedPreferences(PREFS_NAME, 0);
        String url = "http://renudadlani.com";
        webView = (WebView) findViewById(R.id.web1);

        webView.setWebViewClient(new MyWebViewClient());
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('inner-head-wrapper')[0].style.display='none'; })()");
                progressBar.setVisibility(View.GONE);
                Homeactivity.this.progressBar.setProgress(100);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast toast = Toast.makeText(getBaseContext(),
                        "Error: No connection to Internet", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        webView.loadUrl(url);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            Homeactivity.this.progressBar.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }

    public void onResume(){
        super.onResume();
    }
    protected void onDestroy(){
        super.onDestroy();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("admin");
        editor.remove("logged");
        editor.commit();

    }
    public void onBackPressed() {
        progressBar.setVisibility(View.VISIBLE);
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}


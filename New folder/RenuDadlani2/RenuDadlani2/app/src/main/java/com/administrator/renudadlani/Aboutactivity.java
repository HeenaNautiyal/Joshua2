package com.administrator.renudadlani;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Aboutactivity extends AppCompatActivity{
    TextView tv;
    WebView webView;
    ProgressBar progressBar;
    public static final String PREFS_NAME = "LoginPrefs";
    public static final String Password = "admin";
    public static final String fb="neha@outsourcingservicesusa.com";
    SharedPreferences settings,pwd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        settings  = getSharedPreferences(PREFS_NAME, 0);
        webView = (WebView) findViewById(R.id.web1);
        webView.setWebViewClient(new MyWebViewClient());
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        String url = "http://renudadlani.com/about.html";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('inner-head-wrapper')[0].style.display='none'; })()");
                progressBar.setVisibility(View.GONE);
                Aboutactivity.this.progressBar.setProgress(100);
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
            Aboutactivity.this.progressBar.setProgress(0);
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


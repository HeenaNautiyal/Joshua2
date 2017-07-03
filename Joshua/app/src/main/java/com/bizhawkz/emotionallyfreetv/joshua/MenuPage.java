package com.bizhawkz.emotionallyfreetv.joshua;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuPage extends AppCompatActivity {
    ImageView ivTv,ivContact,ivclasses,ivabout,ivshare,ivrateing;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    private void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        ivTv = (ImageView) findViewById(R.id.iv_tv);
        ivContact = (ImageView) findViewById(R.id.iv_contact);
        ivclasses = (ImageView) findViewById(R.id.iv_classes);
        ivabout = (ImageView) findViewById(R.id.iv_about);
        ivshare = (ImageView) findViewById(R.id.iv_share);
        ivrateing = (ImageView) findViewById(R.id.iv_rate);
        ivTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent browserIntent= new Intent(MenuPage.this,Tvseries.class);

                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://emotionallyfree.tv/"));
                // startActivity(browserIntent);
            }
        });
        ivclasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://emotionallyfree.tv/index.php/classes-more/"));
                startActivity(browserIntent);
            }
        });
        ivshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "http://emotionallyfree.tv/";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        ivrateing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));

                }
            }
        });
    }



}

package com.bizhawkz.emotionallyfreetv.joshua;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class HomeScren extends AppCompatActivity {
    private PopupWindow popupWindow;

    private ImageView m_ivImage, m_ivImage1;
    private int m_counter = 0;
    float m_lastTouchX, m_lastTouchY, m_posX, m_posY, m_prevX, m_prevY, m_imgXB, m_imgYB, m_imgXC, m_imgYC, m_dx, m_dy;
    private LinearLayout m_llTop;
    private AbsoluteLayout m_alTop;
    private Context m_context;
    private Button m_btnAddView, m_btnRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scren);
        m_context = this;
        m_prevX = 0;
        m_prevY = 0;
        m_imgXB = 50;
        m_imgYB = 100;
        m_imgXC = 150;
        m_imgYC = 100;
        m_ivImage = (ImageView) findViewById(R.id.ivImage);
        m_ivImage1 = (ImageView) findViewById(R.id.ivImage1);
        m_llTop = (LinearLayout) findViewById(R.id.llTop);
        m_alTop = (AbsoluteLayout) findViewById(R.id.alTop);
        m_ivImage.setOnTouchListener(m_onTouchListener);
        m_ivImage1.setOnTouchListener(m_onTouchListener);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(
                R.color.orange));
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        TextView tv=(TextView)view.findViewById(R.id.textv);
        tv.setText("Home");
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts2/Nexa Bold.otf");
        tv.setTypeface(tf);
        tv.setTextSize(25);
        tv.setTextColor(Color.WHITE);
    }

    /*View.OnTouchListener m_onTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View p_v, MotionEvent p_event)
        {
            switch (p_event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    m_lastTouchX = p_event.getX();
                    m_lastTouchY = p_event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                   if (popupWindow==null) {
                        LayoutInflater layoutInflater =
                                (LayoutInflater) getBaseContext()
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.popup_layout, null);
                        popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        ImageView btntv = (ImageView) popupView.findViewById(R.id.i_podcast);
                        ImageView btnVedio = (ImageView) popupView.findViewById(R.id.i_quantum);
                        ImageView btncomunity = (ImageView) popupView.findViewById(R.id.i_comunity);
                        ImageView btnmore=(ImageView) popupView.findViewById(R.id.i_more);
                        ImageView btnhome=(ImageView) popupView.findViewById(R.id.i_home);

                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);

                        btnVedio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(HomeScren.this, QuantumPage.class);
                                startActivity(it);
                            }
                        });
                        btncomunity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/EmotionallyFreeTV/"));
                                startActivity(browserIntent);
                            }
                        });
                        btntv.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itunes.apple.com/us/podcast/emotionallyfree-tv/id1251883541?mt=2"));
                                startActivity(browserIntent);
                            }
                        });
                        btnmore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent it= new Intent(HomeScren.this,MenuPage.class);
                                startActivity(it);
                            }
                        });
                        btnhome.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                    }
                    if (popupWindow.isShowing())
                    {
                        popupWindow.dismiss();
                    }
                    else{
                        popupWindow.showAsDropDown(m_ivImage, 50, -30);
                    }
                   break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    m_dx = p_event.getX() - m_lastTouchX;
                    m_dy = p_event.getY() - m_lastTouchY;
                    m_posX = m_prevX + m_dx;
                    m_posY = m_prevY + m_dy;
                    if (m_posX > 0 && m_posY > 0 && (m_posX + p_v.getWidth()) < m_alTop.getWidth() && (m_posY + p_v.getHeight()) < m_alTop.getHeight())
                    {
                        p_v.setLayoutParams(new AbsoluteLayout.LayoutParams(p_v.getMeasuredWidth(), p_v.getMeasuredHeight(), (int) m_posX, (int) m_posY));
                        m_prevX = m_posX;
                        m_prevY = m_posY;
                    }
                    break;
                }
            }
            return true;
        }
    };

    private void addView()
    {
        ImageView m_img = new ImageView(m_context);
        TextView m_tv=new TextView(m_context);
        if (m_counter < 1)
        {
            if (m_counter % 2 == 0)
            {
                m_img.setBackgroundResource(R.mipmap.ic_launcher_round);
                m_tv.setText("Hello! Drag Me! ");
                m_alTop.addView(m_tv, new AbsoluteLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, ((int) m_imgXB), ((int) m_imgYB)));
                m_alTop.addView(m_img, new AbsoluteLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, ((int) m_imgXB), ((int) m_imgYB)));
            }
            else
            {
                m_img.setBackgroundResource(R.mipmap.ic_launcher_round);
                m_alTop.addView(m_img, new AbsoluteLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, ((int) m_imgXC), ((int) m_imgYC)));
            }
            m_counter++;
            if (m_counter == 2)
                m_btnAddView.setEnabled(false);
        }
        m_img.setOnTouchListener(m_onTouchListener);
        m_tv.setOnTouchListener(m_onTouchListener);
    }*/

    View.OnTouchListener m_onTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View p_v, MotionEvent p_event)
        {
            switch (p_event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    m_lastTouchX = p_event.getX();
                    m_lastTouchY = p_event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    if (popupWindow==null) {
                        LayoutInflater layoutInflater =
                                (LayoutInflater) getBaseContext()
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.popup_layout, null);
                        popupWindow = new PopupWindow(
                                popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        ImageView btntv = (ImageView) popupView.findViewById(R.id.i_podcast);
                        ImageView btnVedio = (ImageView) popupView.findViewById(R.id.i_quantum);
                        ImageView btncomunity = (ImageView) popupView.findViewById(R.id.i_comunity);
                        ImageView btnmore=(ImageView)popupView.findViewById(R.id.i_more);
                        ImageView btnhome=(ImageView)popupView.findViewById(R.id.i_home);
                        //Set focus disable on screen on click
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);

                        btnVedio.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(HomeScren.this, QuantumPage.class);
                                Bundle bndlanimation =
                                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
                                startActivity(it, bndlanimation);
                                popupWindow.dismiss();
                            }
                        });

                        btncomunity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/EmotionallyFreeTV/"));
                                startActivity(browserIntent);
                            }
                        });

                        btntv.setOnClickListener(new Button.OnClickListener() {

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View v) {
                                Intent slideactivity =new Intent(HomeScren.this,TVseries.class);
                                Bundle bndlanimation =
                                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
                                startActivity(slideactivity, bndlanimation);
                                popupWindow.dismiss();

                            }
                        });
                        btnmore.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View v) {

                                Intent slideactivity = new Intent(HomeScren.this, MenuPage.class);

                                Bundle bndlanimation =
                                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
                                startActivity(slideactivity, bndlanimation);
                                popupWindow.dismiss();
                              }
                        });
                        btnhome.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });

                    }
                    if (popupWindow.isShowing())
                    {
                        popupWindow.dismiss();
                    }
                    else{
                        popupWindow.showAsDropDown(m_ivImage, 50, -30);
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    m_dx = p_event.getX() - m_lastTouchX;
                    m_dy = p_event.getY() - m_lastTouchY;
                    m_posX = m_prevX + m_dx;
                    m_posY = m_prevY + m_dy;
                    if (m_posX > 0 && m_posY > 0 && (m_posX + p_v.getWidth()) < m_alTop.getWidth() && (m_posY + p_v.getHeight()) < m_alTop.getHeight())
                    {
                        p_v.setLayoutParams(new AbsoluteLayout.LayoutParams(p_v.getMeasuredWidth(), p_v.getMeasuredHeight(), (int) m_posX, (int) m_posY));
                        m_prevX = m_posX;
                        m_prevY = m_posY;
                    }
                    break;
                }
            }
            return true;
        }
    };

    private void addView()
    {
        ImageView m_img = new ImageView(m_context);
        TextView m_tv=new TextView(m_context);
        if (m_counter < 1)
        {
            if (m_counter % 2 == 0)
            {
                m_img.setBackgroundResource(R.mipmap.ic_launcher_round);
                m_tv.setText("Hello! Drag Me! ");
                m_alTop.addView(m_tv, new AbsoluteLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, ((int) m_imgXB), ((int) m_imgYB)));
                m_alTop.addView(m_img, new AbsoluteLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, ((int) m_imgXB), ((int) m_imgYB)));
            }
            else
            {
                m_img.setBackgroundResource(R.mipmap.ic_launcher_round);
                m_alTop.addView(m_img, new AbsoluteLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, ((int) m_imgXC), ((int) m_imgYC)));
            }
            m_counter++;
            if (m_counter == 2)
                m_btnAddView.setEnabled(false);
        }
        m_img.setOnTouchListener(m_onTouchListener);
        m_tv.setOnTouchListener(m_onTouchListener);
    }
}

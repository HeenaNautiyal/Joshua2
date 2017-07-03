package com.administrator.addtocart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private int count=0;
    private TextView mCounter;
    String ab;


    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout layout = (LinearLayout)findViewById(R.id.linearMain);
        final Button btn = (Button)findViewById(R.id.second);

        session = new SessionManager(getApplicationContext());

        final Controller ct = (Controller) getApplicationContext();
        mCounter=(TextView)findViewById(R.id.badge_notification_2);

        ModelProducts products = null;
        for(int i= 1; i<=7;i++){
            int Price = 15+ i;
            products = new ModelProducts("Product Item" +i, "Description"+i, Price);
            ct.setProducts(products);
        }
        int productsize = ct.getProductArraylistsize();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        for (int j=0;j< productsize;j++){
            String pName = ct.getProducts(j).getProductName();
            int pPrice = ct.getProducts(j).getProductPrice();
            LinearLayout la = new LinearLayout(this);
            la.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv = new TextView(this);
            tv.setText(" "+pName+" ");
            la.addView(tv);
            TextView tv1 = new TextView(this);
            tv1.setText("Rs"+pPrice+" ");
            la.addView(tv1);

            final Button btn1 = new Button(this);
            btn1.setId(j+1);
            btn1.setText("Add to Cart");
            btn1.setLayoutParams(params);

            final int index = j;
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("TAG", "index:"+index);
                    ModelProducts productsObject = ct.getProducts(index);
                    if(!ct.getCart().CheckProductInCart(productsObject)){
                        btn1.setText("Item Added");
                        ab=ct.getCart().setProducts(productsObject);
                        if (mCounter!=null) {
                            count++;
                            mCounter.setText(Integer.toString(count));
                        }
                    }
                }
            });
            la.addView(btn1);
            layout.addView(la);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(),Screen2.class);
                startActivity(in);
            }
        });
    }
}

package com.heena.testingfirebasecloud;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;
import com.google.android.gms.cast.framework.SessionManager;

/**
 * Created by Heena on 1/19/2017.
 */
public class Screen2 extends Activity {
    SessionManager session;
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);


        TextView showCartContent = (TextView)findViewById(R.id.showcart);
        final Controller ct = (Controller)getApplicationContext();
        final int CartSize = ct.getCart().getCartsize();
        String show = "";
        for(int i=0;i<CartSize;i++){
            String pName = ct.getCart().getProducts(i).getProductName();
            int  pPrice = ct.getCart().getProducts(i).getProductPrice();
            String pDisc = ct.getCart().getProducts(i).getProductDesc();
            show += "Product Name:"+pName+" "+"Price : "+pPrice+""+"Discription : "+pDisc+""+    "-----------------------------------";
        }
        showCartContent.setText(show);
    }
}

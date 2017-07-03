package com.administrator.addtocart;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/28/2016.
 */
public class Controller extends Application {
    private ArrayList<ModelProducts> myproducts = new ArrayList<ModelProducts>();
    private ModelCart myCart = new ModelCart();
    public ModelProducts getProducts(int pPosition){
        return myproducts.get(pPosition);
    }
    public void  setProducts(ModelProducts products){
        myproducts.add(products);
    }

    public ModelCart getCart(){
        return myCart;
    }
    public int  getProductArraylistsize(){
        return myproducts.size();
    }
}

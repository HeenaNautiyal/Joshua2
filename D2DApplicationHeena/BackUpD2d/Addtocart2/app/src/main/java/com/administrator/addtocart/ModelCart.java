package com.administrator.addtocart;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/28/2016.
 */
public class ModelCart {
    private ArrayList<ModelProducts> cartItems = new ArrayList<ModelProducts>();
    public ModelProducts getProducts(int position){
        return cartItems.get(position);
    }
    public String setProducts(ModelProducts Products){
        cartItems.add(Products);
        return null;
    }
    public int getCartsize(){

        return cartItems.size();
    }
    public boolean CheckProductInCart(ModelProducts aproduct){
        return cartItems.contains(aproduct);
    }
}

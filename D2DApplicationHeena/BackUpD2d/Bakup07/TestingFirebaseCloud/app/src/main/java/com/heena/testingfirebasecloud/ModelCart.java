package com.heena.testingfirebasecloud;

import java.util.ArrayList;

/**
 * Created by Heena on 1/11/2017.
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

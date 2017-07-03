package com.wingnity.jsonparsingtutorial;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Heena on 1/24/2017.
 */
public class Controller extends Application{
    private ArrayList myproducts = new ArrayList<>();
    private ModelCart myCart = new ModelCart();
    public Object getProducts(ArrayList<Actors> pPosition){
        return myproducts.get(pPosition);
    }
    public void  setProducts(Actors products){
        myproducts.add(products);
    }

    public ModelCart getCart(){
        return myCart;
    }
    public int  getProductArraylistsize(){
        return myproducts.size();
    }
}

package com.heena.testingfirebasecloud;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Heena on 2/2/2017.
 */
public interface AllApi {
    @FormUrlEncoded
    @POST("insertdata.php")
    public Call<ResponseBody> loginUser(@Field("caseid") String caseid,
                                        @Field("cat_id") String cat1, @Field("sub_cat") String subname,
                                        @Field("medicine_name") String med, @Field("manufacture_by") String menu,
                                        @Field("compositi%20on") String comp, @Field("description") String desc,
                                        @Field("price") String price, @Field("product_image") String im1,
                                        @Field("product_custome") String im2, @Field("added_by") String addby,
                                        @Field("add_date") String adddate, @Field("ip") String ip, @Field("stock") String stk
    );
  }

package org.app.ecommerce_app.api;

import org.app.ecommerce_app.api.SaloonAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitAPI {

    public SaloonAPI retrofitAPI() {

        String BASEURL = "http://182.18.157.215/SaloonApp/API/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SaloonAPI branchAPI = retrofit.create(SaloonAPI.class);
        return branchAPI;
    }
}

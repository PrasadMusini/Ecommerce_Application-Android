package org.app.ecommerce_app.api;


import org.app.ecommerce_app.ecommerce.BannerData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface SaloonAPI {

    @GET("Banner/null")
    Call<BannerData> getBannerData();

}

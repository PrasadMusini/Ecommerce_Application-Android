package org.app.ecommerce_app.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.app.ecommerce_app.R;
import org.app.ecommerce_app.api.RetroFitAPI;
import org.app.ecommerce_app.api.SaloonAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    CategoriesOne_Adapter adapter;
    RecyclerView category1_rv, category2_rv, category3_rv;
    RetroFitAPI retroFitAPI;
    SaloonAPI branchAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        retroFitAPI = new RetroFitAPI();

        category1_rv = findViewById(R.id.category1_rv);
        category2_rv = findViewById(R.id.category2_rv);
        category3_rv = findViewById(R.id.category3_rv);

//        category1_rv
        category1_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new CategoriesOne_Adapter();
        category1_rv.setAdapter(adapter);

//        category2_rv
        category2_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new CategoriesOne_Adapter();
        category2_rv.setAdapter(adapter);


//        category3_rv
        category3_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new CategoriesOne_Adapter();
        category3_rv.setAdapter(adapter);

        getBannersData();
    }

    private void getBannersData() {
        String bannersUrl = "http://182.18.157.215/SaloonApp/API/api/Banner/null";
        String filePathUrl = "http://182.18.157.215/SaloonApp/Saloon_Repo/";

//        branchAPI = retroFitAPI.retrofitAPI();
//        Call<BannerData> dataCall = branchAPI.getBannerData();
        String BASEURL = "http://182.18.157.215/SaloonApp/API/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SaloonAPI api = retrofit.create(SaloonAPI.class);

        Call<BannerData> dataCall = api.getBannerData();

        dataCall.enqueue(new Callback<BannerData>() {
            @Override
            public void onResponse(Call<BannerData> call, Response<BannerData> response) {
                if (response.isSuccessful()) {
                    List<SlideModel> imageList = new ArrayList<>(); // Create image list

                    BannerData bannerData = response.body();
                    ArrayList<BannerData.BannerList> bannerList = bannerData.getBannersList();

                    for (BannerData.BannerList banners : bannerList) {
                        Log.d("TAG", "onResponse: "+banners.getFilePath());

                        imageList.add(new SlideModel(filePathUrl + banners.getFilePath(), ScaleTypes.FIT));

                    }
                    ImageSlider imageSlider = findViewById(R.id.image_slider);
                    imageSlider.setImageList(imageList);
                }
            }

            @Override
            public void onFailure(Call<BannerData> call, Throwable t) {
//                errorToast(t.getMessage(), SaloonActivity.this);
                Log.d("TAG12", "onFailure"+t.getMessage());
                Log.d("TAG12", "onFailure"+t.getCause().toString());
            }
        });
    }
}
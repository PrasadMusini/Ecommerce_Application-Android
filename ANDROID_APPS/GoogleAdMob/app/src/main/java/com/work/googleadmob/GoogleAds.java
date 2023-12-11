package com.work.googleadmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.MobileAdsInitProvider;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class GoogleAds extends AppCompatActivity {

//    public static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";  // testing id
    public static final String AD_UNIT_ID = "ca-app-pub-9959857695039345/8494155429";

    Button interstitialAd_btn;
    AdView bannerAd;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_ads);

        bannerAd = findViewById(R.id.bannerAd);
        interstitialAd_btn = findViewById(R.id.interstitialAd_btn);

        MobileAds.initialize(this, initializationStatus -> {});

        AdRequest adRequest = new AdRequest.Builder().build();

        bannerAd.loadAd(adRequest); // displaying banner ad.

        interstitialAd_btn.setOnClickListener(v -> {

            InterstitialAd.load(this,
                    AD_UNIT_ID,
                    adRequest,
                    new InterstitialAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mInterstitialAd = null;
                }

                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    mInterstitialAd = interstitialAd;
                    mInterstitialAd.show(GoogleAds.this);
                }
            });

        });
    }
}
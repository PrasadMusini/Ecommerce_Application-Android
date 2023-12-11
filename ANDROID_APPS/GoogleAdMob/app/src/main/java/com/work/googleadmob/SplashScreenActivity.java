package com.work.googleadmob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView animated_text;
    private String text = "Hello World!";
    private int currentIndex = 0;
    private boolean increasing = true;
    public static final String TAG = "TAG90";
    public static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        animated_text = findViewById(R.id.animated_text);

        MobileAds.initialize(this, initializationStatus -> {});

        adRequest = new AdRequest.Builder().build();

        animateText();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showAd();
            }
        }, 3000);

        animated_text.setOnClickListener(v -> {
            startActivity(new Intent(this, GoogleAds.class));
        });
    }


    private void showAd() {

        InterstitialAd.load(this, AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(SplashScreenActivity.this);

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d(TAG, "Ad was clicked.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad dismissed fullscreen content.");
                        mInterstitialAd = null;
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.e(TAG, "Ad failed to show fullscreen content.");
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "Ad recorded an impression.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad showed fullscreen content.");
                    }
                });
            }
        });

    }


    private void animateText() {

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (increasing) {
                    animated_text.setText(text.substring(0, currentIndex + 1));
                    currentIndex++;
                    if (currentIndex == text.length()) {
                        increasing = false;
                    }
                } else {
                    animated_text.setText(text.substring(0, currentIndex));
                    currentIndex--;
                    if (currentIndex == 0) {
                        increasing = true;
                    }
                }
                handler.postDelayed(this, 200);
            }
        });
    }
}



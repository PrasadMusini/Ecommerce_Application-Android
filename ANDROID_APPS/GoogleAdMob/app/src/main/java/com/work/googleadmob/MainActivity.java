package com.work.googleadmob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdSize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.work.googleadmob.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;
    private static final String INTERSTITIAL_AD = "ca-app-pub-3940256099942544/1033173712";
    private static final String REWARDED_AD = "ca-app-pub-3940256099942544/5224354917";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, initializationStatus -> {});

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.bannerID.loadAd(adRequest); // Banner load

//        binding.btnRewardedAd.setOnClickListener(view -> {
//            RewardedAd.load(this,
//                    REWARDED_AD,
//                    adRequest,
//                    new RewardedAdLoadCallback() {
//                        @Override
//                        public void onAdFailedToLoad(LoadAdError adError) {
//                            showMessage(adError.getMessage());
//                            mRewardedAd = null;
//                            startActivity(new Intent(MainActivity.this, SecondActivity.class));
//                        }
//
//                        @Override
//                        public void onAdLoaded(RewardedAd rewardedAd) {
//                            showMessage(getString(R.string.ad_info));
//                            mRewardedAd = rewardedAd;
//                            mRewardedAd.show(MainActivity.this, rewardItem -> {
//                                int rewardAmount = rewardItem.getAmount();
//                                showMessage(getString(R.string.rewarded_info) + " " + rewardAmount);
//                            });
//                        }
//                    });
//        });

        binding.btnInterstitialAd.setOnClickListener(view -> {
            InterstitialAd.load(this,
                    INTERSTITIAL_AD,
                    adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            showMessage(adError.getMessage());
                            mInterstitialAd = null;
                            startActivity(new Intent(MainActivity.this, TestActivity.class));
                        }

                        @Override
                        public void onAdLoaded(InterstitialAd interstitialAd) {
                            showMessage(getString(R.string.ad_info));
                            mInterstitialAd = interstitialAd;
                            mInterstitialAd.show(MainActivity.this);
                        }
                    });
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
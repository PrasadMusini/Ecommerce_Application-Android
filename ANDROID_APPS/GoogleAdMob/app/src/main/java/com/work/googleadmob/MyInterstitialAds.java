package com.work.googleadmob;

import android.app.Activity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MyInterstitialAds {

    private Activity activity;
    private InterstitialAd interstitialAds;

    public MyInterstitialAds(Activity activity) {
        this.activity = activity;
    }

    public void loadInterstitialAds(int adUnitId) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                activity,
                activity.getString(adUnitId),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd mInterstitialAds) {
                        interstitialAds = mInterstitialAds;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError p0) {
                        interstitialAds = null;
                    }
                }
        );
    }

    public void showInterstitialAds(Runnable afterSomeCode) {
        if (interstitialAds != null) {
            interstitialAds.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    interstitialAds = null;
                    afterSomeCode.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError p0) {
                    interstitialAds = null;
                    afterSomeCode.run();
                }
            });

            interstitialAds.show(activity);
        } else {
            afterSomeCode.run();
        }
    }
}

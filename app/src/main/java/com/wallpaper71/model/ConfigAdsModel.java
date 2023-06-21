package com.wallpaper71.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigAdsModel {

    @SerializedName("ads_status")
    @Expose
    private String adsStatus;
    @SerializedName("app_open_unit_id")
    @Expose
    private String appOpenUnitId;
    @SerializedName("add_mob_banner_ads_unit_id")
    @Expose
    private String addMobBannerAdsUnitId;
    @SerializedName("add_mob_banner_ads_status")
    @Expose
    private String addMobBannerAdsStatus;
    @SerializedName("add_mob_interstitial_ads_unit_id")
    @Expose
    private String addMobInterstitialAdsUnitId;
    @SerializedName("add_mob_interstitial_ads_status")
    @Expose
    private String addMobInterstitialAdsStatus;

    public String getAdsStatus() {
        return adsStatus;
    }

    public void setAdsStatus(String adsStatus) {
        this.adsStatus = adsStatus;
    }

    public String getAppOpenUnitId() {
        return appOpenUnitId;
    }

    public void setAppOpenUnitId(String appOpenUnitId) {
        this.appOpenUnitId = appOpenUnitId;
    }

    public String getAddMobBannerAdsUnitId() {
        return addMobBannerAdsUnitId;
    }

    public void setAddMobBannerAdsUnitId(String addMobBannerAdsUnitId) {
        this.addMobBannerAdsUnitId = addMobBannerAdsUnitId;
    }

    public String getAddMobBannerAdsStatus() {
        return addMobBannerAdsStatus;
    }

    public void setAddMobBannerAdsStatus(String addMobBannerAdsStatus) {
        this.addMobBannerAdsStatus = addMobBannerAdsStatus;
    }

    public String getAddMobInterstitialAdsUnitId() {
        return addMobInterstitialAdsUnitId;
    }

    public void setAddMobInterstitialAdsUnitId(String addMobInterstitialAdsUnitId) {
        this.addMobInterstitialAdsUnitId = addMobInterstitialAdsUnitId;
    }

    public String getAddMobInterstitialAdsStatus() {
        return addMobInterstitialAdsStatus;
    }

    public void setAddMobInterstitialAdsStatus(String addMobInterstitialAdsStatus) {
        this.addMobInterstitialAdsStatus = addMobInterstitialAdsStatus;
    }
}

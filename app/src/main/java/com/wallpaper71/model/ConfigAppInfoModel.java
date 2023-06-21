package com.wallpaper71.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigAppInfoModel {

    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("about_us_link")
    @Expose
    private String aboutUsLink;
    @SerializedName("app_share_link")
    @Expose
    private String appShareLink;
    @SerializedName("app_logo")
    @Expose
    private String appLogo;

    @SerializedName("full_screen_slider_delay")
    @Expose
    private int fullScreenSliderDelay;

    public int getFullScreenSliderDelay() {
        return fullScreenSliderDelay;
    }

    public void setFullScreenSliderDelay(int fullScreenSliderDelay) {
        this.fullScreenSliderDelay = fullScreenSliderDelay;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAboutUsLink() {
        return aboutUsLink;
    }

    public void setAboutUsLink(String aboutUsLink) {
        this.aboutUsLink = aboutUsLink;
    }

    public String getAppShareLink() {
        return appShareLink;
    }

    public void setAppShareLink(String appShareLink) {
        this.appShareLink = appShareLink;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

}

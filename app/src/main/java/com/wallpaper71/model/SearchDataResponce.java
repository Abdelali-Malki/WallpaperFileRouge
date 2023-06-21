package com.wallpaper71.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchDataResponce {


    @SerializedName("data")
    @Expose
    private List<SearchData> data = null;

    public List<SearchData> getData() {
        return data;
    }

    public void setData(List<SearchData> data) {
        this.data = data;
    }
}

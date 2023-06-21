package com.wallpaper71.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectionResponce {


    @SerializedName("")
    List<CollectionData> collectionData;

    public List<CollectionData> getCollectionData() {
        return collectionData;
    }

    public void setCollectionData(List<CollectionData> categoryData) {
        this.collectionData = categoryData;
    }
}

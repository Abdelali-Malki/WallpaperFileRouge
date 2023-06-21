package com.wallpaper71.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallpaperData {
    @SerializedName("collection")
    @Expose
    private CollectionData collection;
    @SerializedName("products")
    @Expose
    private WallpaperDataProduct products;

    public CollectionData getCollection() {
        return collection;
    }

    public void setCollection(CollectionData collection) {
        this.collection = collection;
    }

    public WallpaperDataProduct getProducts() {
        return products;
    }

    public void setProducts(WallpaperDataProduct products) {
        this.products = products;
    }
}

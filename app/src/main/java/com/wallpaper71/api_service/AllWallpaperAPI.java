package com.wallpaper71.api_service;

import com.wallpaper71.model.CollectionData;
import com.wallpaper71.model.ColorListData;
import com.wallpaper71.model.ConfigAdsModel;
import com.wallpaper71.model.ConfigAppInfoModel;
import com.wallpaper71.model.GroupListData;
import com.wallpaper71.model.SaveSataResponce;
import com.wallpaper71.model.SearchDataResponce;
import com.wallpaper71.model.TotalCountResponce;
import com.wallpaper71.model.WallpaperData;
import com.wallpaper71.model.WallpaperDataProduct;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface AllWallpaperAPI {



    @GET("collections")
    Call<List<CollectionData>>
    CollectionResponce();


    @GET//("collections/1")
    Call<WallpaperData>
    getWallpaperData(@Url String url);

    @GET
    Call<WallpaperDataProduct>
    getWallpaperGroup(@Url String url);

    @GET
    Call<List<GroupListData>>
    getpopularData(@Url String url);

    @GET
    Call<List<ColorListData>>
    getColorData(@Url String url);

    @GET
    Call<SearchDataResponce>
    getSearch(@Url String url);

    @GET("app_info")
    Call<ConfigAppInfoModel>
    getAppInfo();

    @GET("ads_info")
    Call<ConfigAdsModel>
    getAdsInfo();

    @PUT
    Call<TotalCountResponce> totalCount(@Url String url);


    @POST("save-stat")
    @FormUrlEncoded
    Call<SaveSataResponce> postSaveSta(@Field("action_type") String action_type,
                                        @Field("action_id") String action_id,
                                        @Field("platform") String platform,
                                        @Field("source_page") String source_page);
}

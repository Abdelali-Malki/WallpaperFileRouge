package com.wallpaper71.api_service;

import com.wallpaper71.config.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
AllWallpaperService {

        public  Retrofit retrofit=null;
        public AllWallpaperAPI allWallpaperAPI(){
                if (retrofit==null){
                        retrofit=new Retrofit.Builder()
                                .baseUrl(BaseUrl.baseUrl+"api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                }
             return retrofit.create(AllWallpaperAPI.class);
        }

        public AllWallpaperAPI getAllCategoryAPI(){
                if (retrofit==null){
                        retrofit=new Retrofit.Builder()
                                .baseUrl(BaseUrl.baseUrl+"api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                }
                return retrofit.create(AllWallpaperAPI.class);
        }


}

package com.wallpaper71.presenter;

import android.util.Log;

import com.wallpaper71.api_service.AllWallpaperService;
import com.wallpaper71.fragment.PagerView;
import com.wallpaper71.model.CollectionData;
import com.wallpaper71.model.SaveSataResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wallpaper71.config.app_config.BaseText.errorMsg;

public class CollectionPresenter {

    PagerView pagerView;
    AllWallpaperService allWallpaperService=null;

    public CollectionPresenter(PagerView pagerView ){
        this.pagerView=pagerView;

        if (this.allWallpaperService==null){
            this.allWallpaperService=new AllWallpaperService();
        }

        pagerView.setupUI();

        pagerView.showSwipeRefresh();
    }


    public void saveSta(String ProductView,String action_id,String source_page ){
        allWallpaperService
                .getAllCategoryAPI()
                .postSaveSta(ProductView,action_id,"Android",source_page)
                .enqueue(new Callback<SaveSataResponce>() {
                    @Override
                    public void onResponse(Call<SaveSataResponce> call, Response<SaveSataResponce> response) {
                        Log.d("getAppInfo",response.body()+" dfd");
                        if (response.code()==200){

                            // mainView.appInfo(response.body());
                        }else {

                        }


                    }

                    @Override
                    public void onFailure(Call<SaveSataResponce> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");


                    }
                });


    }
    public void getCollectionData(){
        allWallpaperService
                .getAllCategoryAPI()
                .CollectionResponce()
                .enqueue(new Callback<List<CollectionData>>() {
                    @Override
                    public void onResponse(Call<List<CollectionData>> call, Response<List<CollectionData>> response) {

                     //   List <CollectionData> collectionData=response.body();
                      //  mainView.collectionData(collectionData);

                        if (response.code()==200){
                            Log.d("allWallpaperData",response+" dfd");
                            List <CollectionData> collectionData=response.body();
                            //  List<AllWallpaperData> allWallpaperData=allWallpaperResponce.getAllWallpaperDataResponce();
                            pagerView.collectionData(collectionData);
                            pagerView.hideSwipeRefresh();
                        }else {
                            pagerView.showMessage("Page Not Found");
                            pagerView.hideSwipeRefresh();
                        }


                    }

                    @Override
                    public void onFailure(Call<List<CollectionData>> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                        pagerView.showMessage(errorMsg);
                        pagerView.hideSwipeRefresh();
                    }
                });


    }
}

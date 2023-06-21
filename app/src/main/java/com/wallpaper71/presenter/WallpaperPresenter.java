package com.wallpaper71.presenter;


import android.util.Log;

import com.wallpaper71.api_service.AllWallpaperService;
import com.wallpaper71.fragment.PagerView;
import com.wallpaper71.model.ColorListData;
import com.wallpaper71.model.GroupListData;
import com.wallpaper71.model.SaveSataResponce;
import com.wallpaper71.model.WallpaperData;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.model.WallpaperDataProduct;
import com.wallpaper71.view.ListByCollection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wallpaper71.config.app_config.BaseText.errorMsg;


public class WallpaperPresenter  {
    PagerView pagerView;
    ListByCollection activity;
    AllWallpaperService allWallpaperService=null;

    public WallpaperPresenter(PagerView pagerView ){
        this.pagerView=pagerView;

        if (this.allWallpaperService==null){
            this.allWallpaperService=new AllWallpaperService();
        }

        pagerView.setupUI();

        pagerView.showSwipeRefresh();
    }

    public WallpaperPresenter(ListByCollection activity ){
        this.activity=activity;

        if (this.allWallpaperService==null){
            this.allWallpaperService=new AllWallpaperService();
        }

        activity.setupUI();

        activity.showSwipeRefresh();
    }

    public void saveSta(String ProductView,String action_id,String source_page ){
        Log.d("dsfsf",ProductView+" ; "+action_id+" ; "+source_page);
        allWallpaperService
                .getAllCategoryAPI()
                .postSaveSta(ProductView,action_id,"Android",source_page)
                .enqueue(new Callback<SaveSataResponce>() {
                    @Override
                    public void onResponse(Call<SaveSataResponce> call, Response<SaveSataResponce> response) {
                       // Log.d("getAppInfo",response.code()+" dfd");
                     //   Log.d("getAppInfo",response.body().getStatus().toString()+" dfd");
                        if (response.code()==200){

                            // mainView.appInfo(response.body());
                        }else {

                        }


                    }

                    @Override
                    public void onFailure(Call<SaveSataResponce> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                       // mainView.showMessage(errorMsg);

                    }
                });


    }
    public void getWallpaperData(String url){
        allWallpaperService
                .allWallpaperAPI()
                .getWallpaperData(url)
                .enqueue(new Callback<WallpaperData>() {
                    @Override
                    public void onResponse(Call<WallpaperData> call, Response<WallpaperData> response) {
                        if (response.code()==200){
                           // Log.d("allWallpaperData22",response+" dfd");
                            WallpaperData  wallpaperData=response.body();
                            WallpaperDataProduct allWallpaperDataProduct=wallpaperData.getProducts();
                            List<WallpaperDataAll> allWallpaperData=allWallpaperDataProduct.getData();
                            pagerView.wallpaperData(allWallpaperData,allWallpaperDataProduct);
                            pagerView.hideSwipeRefresh();
                        }else {

                            pagerView.showMessage("Page Not Found");
                            pagerView.hideSwipeRefresh();
                        }


                    }

                    @Override
                    public void onFailure(Call<WallpaperData> call, Throwable t) {

                        pagerView.showMessage(errorMsg);
                        pagerView.hideSwipeRefresh();
                    }
                });


    }

    public void getWallpaperDataHome(String url){

        allWallpaperService
                .allWallpaperAPI()
                .getWallpaperGroup(url)
                .enqueue(new Callback<WallpaperDataProduct>() {
                    @Override
                    public void onResponse(Call<WallpaperDataProduct> call, Response<WallpaperDataProduct> response) {


                        if (response.code()==200){

                            WallpaperDataProduct  wallpaperDataProduct=response.body();
                            pagerView.wallByGroup(wallpaperDataProduct);
                            pagerView.hideSwipeRefresh();
                        }else {
                            //  activity.showMessage("Page Not Found");
                            pagerView.hideSwipeRefresh();
                        }
                    }

                    @Override
                    public void onFailure(Call<WallpaperDataProduct> call, Throwable t) {

                        pagerView.showMessage(errorMsg);
                        pagerView.hideSwipeRefresh();
                    }
                });

    }



    public void getWallpaperDataByCollectio(String url){
        Log.d("url12",url+" dfd");
        allWallpaperService
                .allWallpaperAPI()
                .getWallpaperData(url)
                .enqueue(new Callback<WallpaperData>() {
                    @Override
                    public void onResponse(Call<WallpaperData> call, Response<WallpaperData> response) {

                        if (response.code()==200){
                            Log.d("allWallpaperData12",response.body()+" dfd");
                            WallpaperData  wallpaperData=response.body();
                            WallpaperDataProduct allWallpaperDataProduct=wallpaperData.getProducts();
                            List<WallpaperDataAll> allWallpaperData=allWallpaperDataProduct.getData();
                            activity.wallpaperData(allWallpaperData,allWallpaperDataProduct);
                            Log.d("allWallpaperData123",allWallpaperData.size()+" dfd");

                            activity.hideSwipeRefresh();
                        }else {


                            activity.hideSwipeRefresh();
                        }
                    }

                    @Override
                    public void onFailure(Call<WallpaperData> call, Throwable t) {
                        Log.d("allWallpaperDataff",t.getMessage()+" dfdff");
                        activity.showMessage(errorMsg);
                        activity.hideSwipeRefresh();
                    }
                });


    }
    public void getWallpaperDataByCollectioLoadMore(String url){
        allWallpaperService
                .allWallpaperAPI()
                .getWallpaperData(url)
                .enqueue(new Callback<WallpaperData>() {
                    @Override
                    public void onResponse(Call<WallpaperData> call, Response<WallpaperData> response) {

                        if (response.code()==200){
                            Log.d("allWallpaperData122",response.body()+" dfd");
                            WallpaperData  wallpaperData=response.body();
                            WallpaperDataProduct allWallpaperDataProduct=wallpaperData.getProducts();
                            List<WallpaperDataAll> allWallpaperData=allWallpaperDataProduct.getData();
                          //  activity.wallpaperData(allWallpaperData,allWallpaperDataProduct);
                          //  activity.hideSwipeRefresh();
                        }else {


                          //  activity.hideSwipeRefresh();
                        }
                    }

                    @Override
                    public void onFailure(Call<WallpaperData> call, Throwable t) {
                        Log.d("allWallpaperDataff",t.getMessage()+" dfdff");
                        activity.showMessage(errorMsg);
                        activity.hideSwipeRefresh();
                    }
                });

    }
    public void getWallpaperDataByGroup(String url){
        Log.d("allurl",url);
        allWallpaperService
                .allWallpaperAPI()
                .getWallpaperGroup(url)
                .enqueue(new Callback<WallpaperDataProduct>() {
                    @Override
                    public void onResponse(Call<WallpaperDataProduct> call, Response<WallpaperDataProduct> response) {



                        if (response.code()==200){
                            Log.d("allWallpaperData1",response.body()+" dfd");
                            WallpaperDataProduct  wallpaperDataProduct=response.body();
                            activity.wallByGroup(wallpaperDataProduct);

                            activity.hideSwipeRefresh();

                        }else {

                          //  activity.showMessage("Page Not Found");
                            activity.hideSwipeRefresh();
                        }
                    }

                    @Override
                    public void onFailure(Call<WallpaperDataProduct> call, Throwable t) {
                        Log.d("allWallpaperDataff",t.getMessage()+" dfdff");

                        activity.showMessage(errorMsg);
                        activity.hideSwipeRefresh();
                    }
                });


    }
    public void getPopular(String url){
        allWallpaperService
                .getAllCategoryAPI()
                .getpopularData(url)
                .enqueue(new Callback<List<GroupListData>>() {
                    @Override
                    public void onResponse(Call<List<GroupListData>> call, Response<List<GroupListData>> response) {

                        //   List <CollectionData> collectionData=response.body();
                        //  mainView.collectionData(collectionData);

                        if (response.code()==200){
                            List <GroupListData> groupListResponceList=response.body();
                            Log.d("allGroupData",groupListResponceList.size()+" dfd");
                            //  List<AllWallpaperData> allWallpaperData=allWallpaperResponce.getAllWallpaperDataResponce();
                            pagerView.groupList(groupListResponceList);

                        }else {

                            pagerView.showMessage("Page Not Found");

                        }



                    }

                    @Override
                    public void onFailure(Call<List<GroupListData>> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                        pagerView.showMessage(errorMsg);

                    }
                });


    }

    public void getColor(String url){
        allWallpaperService
                .getAllCategoryAPI()
                .getColorData(url)
                .enqueue(new Callback<List<ColorListData>>() {
                    @Override
                    public void onResponse(Call<List<ColorListData>> call, Response<List<ColorListData>> response) {

                        //   List <CollectionData> collectionData=response.body();
                        //  mainView.collectionData(collectionData);

                        if (response.code()==200){
                            List <ColorListData> groupListResponceList=response.body();
                            Log.d("allGroupData",groupListResponceList.size()+" dfd");
                            //  List<AllWallpaperData> allWallpaperData=allWallpaperResponce.getAllWallpaperDataResponce();
                            pagerView.colorList(groupListResponceList);

                        }else {

                            pagerView.showMessage("Page Not Found");

                        }



                    }

                    @Override
                    public void onFailure(Call<List<ColorListData>> call, Throwable t) {
                        Log.d("CategoryResponceff",t.getMessage()+" cat");
                        pagerView.showMessage(errorMsg);

                    }
                });


    }
}

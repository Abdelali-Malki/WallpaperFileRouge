package com.wallpaper71.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.wallpaper71.R;
import com.wallpaper71.adapter.ColorListAdapter;
import com.wallpaper71.adapter.GroupListAdapter;
import com.wallpaper71.adapter.WallpaperPopularAdapter;
import com.wallpaper71.model.CollectionData;
import com.wallpaper71.model.ColorListData;
import com.wallpaper71.model.GroupListData;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.model.WallpaperDataProduct;
import com.wallpaper71.presenter.WallpaperPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.shimmer.ShimmerFrameLayout;

public class FragmentView extends Fragment implements PagerView{

    private static FragmentView instance;
    private View view = null;

    ProgressDialog progressDialog;
    List<WallpaperDataAll> allWallpaperDataList;
    List<GroupListData> groupListDataList;
    List<ColorListData> colorDataList;
    WallpaperPopularAdapter wallpaperListAdapter;
    GroupListAdapter groupListAdapter;
    ColorListAdapter colorListAdapter;

    @BindView(R.id.itemsRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.groupRecyclerView)
    RecyclerView groupRecyclerView;

    @BindView(R.id.colorGroupRecyclerView)
    RecyclerView colorGroupRecyclerView;


    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;

    @BindView(R.id.placeholder_popular)
    ShimmerFrameLayout placeholder_popular;


    @BindView(R.id.placeholder_color)
    ShimmerFrameLayout placeholder_color;

    @BindView(R.id.idPBLoading)
    ProgressBar idPBLoading;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    WallpaperPresenter wallpaperPresenter;
    int page=1;
    boolean refresh=false;
    public static void start(Context context) {
        Intent intent = new Intent(context, FragmentView.class);
        context.startActivity(intent);
    }

    public static FragmentView getInstance() {
        if (instance == null) instance = new FragmentView();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getContext()).inflate(R.layout.fragment_view, container, false);
        ButterKnife.bind(this,view);

        init();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                init();
            }
        });


        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                    page++;
                    refresh=false;
                   // mSwipeRefreshLayout.setRefreshing(true);
                    Log.d("page22",page+"");
                    idPBLoading.setVisibility(View.VISIBLE);
                    wallpaperPresenter.getWallpaperDataHome("products/popular"+"/30"+"/30"+"/"+page);
                }else {

                }
            }
        });


        return view;
    }

    public void init(){
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        placeholder_color.setVisibility(View.VISIBLE);
        placeholder_color.startShimmer();
        placeholder_popular.setVisibility(View.VISIBLE);
        placeholder_popular.startShimmer();

        page=1;
        refresh=true;
         wallpaperPresenter=new WallpaperPresenter(this);
        wallpaperPresenter.getWallpaperDataHome("products/popular");

        wallpaperPresenter.getPopular("featured-group");
        wallpaperPresenter.getColor("color-group");//2323
    }
    @Override
    public void setupUI() {
        progressDialog=new ProgressDialog(getContext());
        allWallpaperDataList=new ArrayList<>();
        wallpaperListAdapter=new WallpaperPopularAdapter(allWallpaperDataList,getContext(),"Most-Popular");

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(wallpaperListAdapter);
       // recyclerView.setNestedScrollingEnabled(false);
        //=========== groupListData=====

        groupListDataList=new ArrayList<>();
        groupListAdapter=new GroupListAdapter(groupListDataList,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       // RecyclerView.LayoutManager mLayoutManagerGroup = new GridLayoutManager(getContext(), 2);
        groupRecyclerView.setLayoutManager(linearLayoutManager);
        groupRecyclerView.setAdapter(groupListAdapter);

        //=========== groupListData=====

        colorDataList=new ArrayList<>();
        colorListAdapter=new ColorListAdapter(colorDataList,getContext());
        LinearLayoutManager linearLayoutManagerColor=new LinearLayoutManager(getContext());
        linearLayoutManagerColor.setOrientation(LinearLayoutManager.HORIZONTAL);
        // RecyclerView.LayoutManager mLayoutManagerGroup = new GridLayoutManager(getContext(), 2);
        colorGroupRecyclerView.setLayoutManager(linearLayoutManagerColor);
        colorGroupRecyclerView.setAdapter(colorListAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message+"", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void collectionData(List<CollectionData> allCollection) {

    }

    @Override
    public void wallpaperData(List<WallpaperDataAll> wallpaperDataAll, WallpaperDataProduct wallpaperDataProduct) {
        //allWallpaperDataList.clear();

        allWallpaperDataList.addAll(wallpaperDataAll);
        //Collections.shuffle(allWallpaperDataList);
      //  Toast.makeText(getContext(), "ok"+allWallpaperDataList.size(), Toast.LENGTH_SHORT).show();
        wallpaperListAdapter.notifyDataSetChanged();
    }

    @Override
    public void groupList(List<GroupListData> groupListData) {
        groupListDataList.clear();
        placeholder_popular.stopShimmer();

        placeholder_popular.setVisibility(View.GONE);
        groupListDataList.addAll(groupListData);

        groupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void colorList(List<ColorListData> groupListData) {
        colorDataList.clear();
        placeholder_color.stopShimmer();
        placeholder_color.setVisibility(View.GONE);
        colorDataList.addAll(groupListData);

       // Log.d("titleList",groupListData.get(0).getId());
        colorListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage("Loading...");
        } else {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            try {
                progressDialog.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void hideProgressDialog() {
        try {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showSwipeRefresh() {
        idPBLoading.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);


    }

    @Override
    public void hideSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void wallByGroup(WallpaperDataProduct wallpaperDataProduct) {


        if (refresh){
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            allWallpaperDataList.clear();
           // Toast.makeText(getContext(), "okk", Toast.LENGTH_SHORT).show();
        }else {
            idPBLoading.setVisibility(View.GONE);
        }
        allWallpaperDataList.addAll(wallpaperDataProduct.getData());
        // Toast.makeText(getContext(), "ok"+allWallpaperDataList.size(), Toast.LENGTH_SHORT).show();
        wallpaperListAdapter.notifyDataSetChanged();
    }



    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}

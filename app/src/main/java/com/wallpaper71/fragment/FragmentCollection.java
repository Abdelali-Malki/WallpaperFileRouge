package com.wallpaper71.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wallpaper71.R;
import com.wallpaper71.adapter.CollectionListAdapter;
import com.wallpaper71.model.CollectionData;
import com.wallpaper71.model.ColorListData;
import com.wallpaper71.model.GroupListData;
import com.wallpaper71.model.WallpaperDataAll;
import com.wallpaper71.model.WallpaperDataProduct;
import com.wallpaper71.presenter.CollectionPresenter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCollection extends Fragment implements PagerView{
    private static FragmentView instance;
    private View view = null;

    ProgressDialog progressDialog;
    List<CollectionData> collectionDataList;
    CollectionListAdapter collectionListAdapter;

    @BindView(R.id.itemsRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getContext()).inflate(R.layout.collection_view, container, false);
        ButterKnife.bind(this,view);

        init();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                init();
            }
        });
        return view;
    }
    public void init(){
        CollectionPresenter collectionPresenter=new CollectionPresenter(this);
        collectionPresenter.getCollectionData();
    }



    @Override
    public void setupUI() {
        progressDialog=new ProgressDialog(getContext());
        collectionDataList=new ArrayList<>();
        collectionListAdapter=new CollectionListAdapter(collectionDataList,getContext());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(collectionListAdapter);
    }

    @Override
    public void showMessage(String message) {

    }



    @Override
    public void collectionData(List<CollectionData> allCollection) {
        collectionDataList.clear();

        collectionDataList.addAll(allCollection);
        //Collections.shuffle(collectionDataList);
        collectionListAdapter.notifyDataSetChanged();
    }

    @Override
    public void wallpaperData(List<WallpaperDataAll> wallpaperDataAll, WallpaperDataProduct wallpaperDataProduct) {

    }

    @Override
    public void groupList(List<GroupListData> groupListData) {

    }

    @Override
    public void colorList(List<ColorListData> groupListData) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void wallByGroup(WallpaperDataProduct wallpaperDataProduct) {

    }



}

package com.wallpaper71.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wallpaper71.R;
import com.wallpaper71.model.SearchData;
import com.wallpaper71.view.ListByCollection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.WallpapersHolder> {

    List<SearchData> searchData;
    Context context;

    public SearchListAdapter(List<SearchData> searchData, Context context) {
        this.searchData = searchData;
        this.context = context;
    }

    @Override
    public SearchListAdapter.WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        SearchListAdapter.WallpapersHolder mh = new SearchListAdapter.WallpapersHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SearchListAdapter.WallpapersHolder holder, int position) {
        holder.title.setText(searchData.get(position).getName());
        //Glide.with(context).load(collectionData.get(position).getCoverPhoto()).into(holder.collectionImg);



        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ListByCollection.class);
                intent.putExtra("name",searchData.get(position).getName());
                intent.putExtra("id",searchData.get(position).getId()+"");
                intent.putExtra("data_from","search");

                intent.putExtra("action_type","Search-View");
                intent.putExtra("from","Home");
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchData.size();
    }

    public class WallpapersHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.title)
        TextView title;

        public WallpapersHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
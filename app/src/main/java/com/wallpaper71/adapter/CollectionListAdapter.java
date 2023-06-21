package com.wallpaper71.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wallpaper71.R;
import com.wallpaper71.model.CollectionData;
import com.wallpaper71.view.ListByCollection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wallpaper71.config.BaseUrl.baseUrl;
import static com.wallpaper71.config.BaseUrl.collectionsImageDir;

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.WallpapersHolder> {

    List<CollectionData> collectionData;
    Context context;

    public CollectionListAdapter(List<CollectionData> collectionData, Context context) {
        this.collectionData = collectionData;
        this.context = context;

    }

    @Override
    public CollectionListAdapter.WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.collection_item,parent,false);
        CollectionListAdapter.WallpapersHolder mh = new CollectionListAdapter.WallpapersHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(CollectionListAdapter.WallpapersHolder holder, int position) {

        holder.title.setText(collectionData.get(position).getName());
        //Glide.with(context).load(collectionData.get(position).getCoverPhoto()).into(holder.collectionImg);
        Glide.with(context).load(baseUrl+collectionsImageDir+collectionData.get(position).getCoverPhoto()).into(holder.collectionImg);
        holder.collectionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ListByCollection.class);
                intent.putExtra("name",collectionData.get(position).getName());
                intent.putExtra("id",collectionData.get(position).getId()+"");
                intent.putExtra("data_from","collection");
                intent.putExtra("from", "Collection");
                intent.putExtra("action_type","Collection-View");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionData.size();
    }

    public class WallpapersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.collectionImg)
        ImageView collectionImg;

        @BindView(R.id.title)
        TextView title;

        public WallpapersHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
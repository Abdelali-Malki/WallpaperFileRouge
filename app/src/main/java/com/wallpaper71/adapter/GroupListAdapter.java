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
import com.wallpaper71.model.GroupListData;
import com.wallpaper71.view.ListByCollection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.WallpapersHolder> {

    List<GroupListData> groupListData;
    Context context;

    public GroupListAdapter(List<GroupListData> groupListData, Context context) {
        this.groupListData = groupListData;
        this.context = context;
    }

    @Override
    public GroupListAdapter.WallpapersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.group_item,parent,false);
        GroupListAdapter.WallpapersHolder mh = new GroupListAdapter.WallpapersHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(GroupListAdapter.WallpapersHolder holder, int position) {
        holder.title.setText(groupListData.get(position).getName());
        Glide.with(context).load(groupListData.get(position).getIcon()).into(holder.collectionImg);
       // Glide.with(context).load("http://brodiesprintshop.com/wp-content/uploads/2014/04/bokeh-cover-bg.jpg").into(holder.collectionImg);
        holder.collectionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ListByCollection.class);
                intent.putExtra("name",groupListData.get(position).getName());
                intent.putExtra("id",groupListData.get(position).getId()+"");
                intent.putExtra("data_from","group");
                intent.putExtra("action_type",groupListData.get(position).getName()+"-View");
                intent.putExtra("from","Home");

                context.startActivity(intent);
            }
        });

        /*switch (position){
            case 0:
                Glide.with(context).load("https://cdn.pixabay.com/photo/2016/04/13/20/36/mercedes-1327610_1280.jpg").into(holder.collectionImg);
                break;
            case 1:
                Glide.with(context).load("https://cdn.pixabay.com/photo/2015/10/30/20/13/boat-1014711_1280.jpg").into(holder.collectionImg);
                break;
            case 2:
                Glide.with(context).load("data:image/jpeg;base64,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").into(holder.collectionImg);
                break;
            case 3:
                Glide.with(context).load("https://img.freepik.com/free-vector/green-abstract-geometric-background_23-2148366726.jpg?size=626&ext=jpg").into(holder.collectionImg);
                break;
            case 4:
                Glide.with(context).load("data:image/jpeg;base64,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").into(holder.collectionImg);
                break;
            default:
                Glide.with(context).load("data:image/jpeg;base64,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").into(holder.collectionImg);

        }*/
    }

    @Override
    public int getItemCount() {
        return groupListData.size();
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
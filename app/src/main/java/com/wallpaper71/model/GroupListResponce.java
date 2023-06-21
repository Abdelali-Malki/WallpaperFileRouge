package com.wallpaper71.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupListResponce {


    @SerializedName("")
    List<GroupListData> groupListData;

    public List<GroupListData> getGroupListData() {
        return groupListData;
    }

    public void setGroupListData(List<GroupListData> groupListData) {
        this.groupListData = groupListData;
    }
}

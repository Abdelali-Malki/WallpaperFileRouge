package com.wallpaper71.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallpaperDataAll {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("collections")
    @Expose
    private String collections;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("total_view_count")
    @Expose
    private String totalViewCount;
    @SerializedName("total_download_count")
    @Expose
    private String totalDownloadCount;
    @SerializedName("last_view_datetime")
    @Expose
    private String lastViewDatetime;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCollections() {
        return collections;
    }

    public void setCollections(String collections) {
        this.collections = collections;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTotalViewCount() {
        return totalViewCount;
    }

    public void setTotalViewCount(String totalViewCount) {
        this.totalViewCount = totalViewCount;
    }

    public String getTotalDownloadCount() {
        return totalDownloadCount;
    }

    public void setTotalDownloadCount(String totalDownloadCount) {
        this.totalDownloadCount = totalDownloadCount;
    }

    public String getLastViewDatetime() {
        return lastViewDatetime;
    }

    public void setLastViewDatetime(String lastViewDatetime) {
        this.lastViewDatetime = lastViewDatetime;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
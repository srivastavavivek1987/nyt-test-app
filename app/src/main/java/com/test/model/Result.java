package com.test.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    private String title;
    @SerializedName("abstract")
    private String desc;
    private String url;
    @SerializedName("byline")
    private String byLine;
    @SerializedName("updated_date")
    private String updatedDate;
    private String imageUrl;
    private List<Image> multimedia;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setMultimedia(List<Image> multimedia) {
        this.multimedia = multimedia;
    }

    public String getTitle() {
        return title;
    }


    public String getDesc() {
        return desc;
    }


    public String getUrl() {
        return url;
    }

    public String getByLine() {
        return byLine;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public List<Image> getMultimedia() {
        return multimedia;
    }
}

package com.example.trip_plan_budget.model.hotel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Large implements Parcelable {

    public final static Creator<Large> CREATOR = new Creator<Large>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Large createFromParcel(Parcel in) {
            return new Large(in);
        }

        public Large[] newArray(int size) {
            return (new Large[size]);
        }

    };
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("height")
    @Expose
    private String height;

    protected Large(Parcel in) {
        this.width = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Large() {
    }

    /**
     * @param width
     * @param url
     * @param height
     */
    public Large(String width, String url, String height) {
        super();
        this.width = width;
        this.url = url;
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(width);
        dest.writeValue(url);
        dest.writeValue(height);
    }

    public int describeContents() {
        return 0;
    }

}

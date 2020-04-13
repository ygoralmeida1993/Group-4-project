package com.example.trip_plan_budget.model.hotel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

    public final static Creator<Photo> CREATOR = new Creator<Photo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return (new Photo[size]);
        }

    };
    @SerializedName("images")
    @Expose
    private Images images;

    protected Photo(Parcel in) {
        this.images = ((Images) in.readValue((Images.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Photo() {
    }

    /**
     * @param images
     */
    public Photo(Images images) {
        super();
        this.images = images;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(images);
    }

    public int describeContents() {
        return 0;
    }

}

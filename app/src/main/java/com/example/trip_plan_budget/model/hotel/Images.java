package com.example.trip_plan_budget.model.hotel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images implements Parcelable {

    public final static Creator<Images> CREATOR = new Creator<Images>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        public Images[] newArray(int size) {
            return (new Images[size]);
        }

    };
    @SerializedName("large")
    @Expose
    private Large large;

    protected Images(Parcel in) {
        this.large = ((Large) in.readValue((Large.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Images() {
    }

    /**
     * @param large
     */
    public Images(Large large) {
        super();
        this.large = large;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(large);
    }

    public int describeContents() {
        return 0;
    }

}

package com.example.trip_plan_budget.model.places;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Point implements Parcelable {

    public final static Creator<Point> CREATOR = new Creator<Point>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        public Point[] newArray(int size) {
            return (new Point[size]);
        }

    };
    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("lat")
    @Expose
    private double lat;

    protected Point(Parcel in) {
        this.lon = ((double) in.readValue((double.class.getClassLoader())));
        this.lat = ((double) in.readValue((double.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Point() {
    }

    /**
     * @param lon
     * @param lat
     */
    public Point(double lon, double lat) {
        super();
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Point withLon(double lon) {
        this.lon = lon;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Point withLat(double lat) {
        this.lat = lat;
        return this;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lon);
        dest.writeValue(lat);
    }

    public int describeContents() {
        return 0;
    }

}

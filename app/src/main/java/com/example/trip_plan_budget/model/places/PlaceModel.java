package com.example.trip_plan_budget.model.places;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceModel implements Parcelable {

    public final static Parcelable.Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PlaceModel createFromParcel(Parcel in) {
            return new PlaceModel(in);
        }

        public PlaceModel[] newArray(int size) {
            return (new PlaceModel[size]);
        }

    };
    @SerializedName("xid")
    @Expose
    private String xid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dist")
    @Expose
    private double dist;
    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("kinds")
    @Expose
    private String kinds;
    @SerializedName("point")
    @Expose
    private Point point;

    protected PlaceModel(Parcel in) {
        this.xid = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((double) in.readValue((double.class.getClassLoader())));
        this.rate = ((int) in.readValue((int.class.getClassLoader())));
        this.kinds = ((String) in.readValue((String.class.getClassLoader())));
        this.point = ((Point) in.readValue((Point.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public PlaceModel() {
    }

    /**
     * @param xid
     * @param rate
     * @param name
     * @param dist
     * @param kinds
     * @param point
     */
    public PlaceModel(String xid, String name, double dist, int rate, String kinds, Point point) {
        super();
        this.xid = xid;
        this.name = name;
        this.dist = dist;
        this.rate = rate;
        this.kinds = kinds;
        this.point = point;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public PlaceModel withXid(String xid) {
        this.xid = xid;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlaceModel withName(String name) {
        this.name = name;
        return this;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public PlaceModel withDist(double dist) {
        this.dist = dist;
        return this;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public PlaceModel withRate(int rate) {
        this.rate = rate;
        return this;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public PlaceModel withKinds(String kinds) {
        this.kinds = kinds;
        return this;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public PlaceModel withPoint(Point point) {
        this.point = point;
        return this;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(xid);
        dest.writeValue(name);
        dest.writeValue(dist);
        dest.writeValue(rate);
        dest.writeValue(kinds);
        dest.writeValue(point);
    }

    public int describeContents() {
        return 0;
    }

}


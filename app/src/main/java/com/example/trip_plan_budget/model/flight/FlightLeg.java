package com.example.trip_plan_budget.model.flight;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FlightLeg implements Parcelable {

    public static final Creator<FlightLeg> CREATOR = new Creator<FlightLeg>() {
        @Override
        public FlightLeg createFromParcel(Parcel source) {
            return new FlightLeg(source);
        }

        @Override
        public FlightLeg[] newArray(int size) {
            return new FlightLeg[size];
        }
    };
    @SerializedName("CarrierIds")
    private List<Integer> carriers;
    @SerializedName("OriginId")
    private int originID;
    @SerializedName("DestinationId")
    private int destID;
    @SerializedName("DepartureDate")
    private String deptDate;

    public FlightLeg() {
    }

    protected FlightLeg(Parcel in) {
        this.carriers = new ArrayList<Integer>();
        in.readList(this.carriers, Integer.class.getClassLoader());
        this.originID = in.readInt();
        this.destID = in.readInt();
        this.deptDate = in.readString();
    }

    public List<Integer> getCarriers() {
        return carriers;
    }

    public int getOriginID() {
        return originID;
    }

    public int getDestID() {
        return destID;
    }

    public String getDeptDate() {
        return deptDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.carriers);
        dest.writeInt(this.originID);
        dest.writeInt(this.destID);
        dest.writeString(this.deptDate);
    }
}

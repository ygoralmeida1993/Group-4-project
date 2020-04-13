package com.example.trip_plan_budget.model.flight;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PriceModel implements Parcelable {
    public static final Creator<PriceModel> CREATOR = new Creator<PriceModel>() {
        @Override
        public PriceModel createFromParcel(Parcel source) {
            return new PriceModel(source);
        }

        @Override
        public PriceModel[] newArray(int size) {
            return new PriceModel[size];
        }
    };
    @SerializedName("MinPrice")
    private double price;
    @SerializedName("Direct")
    private boolean directRoute;
    @SerializedName("InboundLeg")
    private FlightLeg inBoundLeg;
    @SerializedName("OutboundLeg")
    private FlightLeg outBoundLeg;

    public PriceModel() {
    }

    protected PriceModel(Parcel in) {
        this.price = in.readDouble();
        this.directRoute = in.readByte() != 0;
        this.inBoundLeg = in.readParcelable(FlightLeg.class.getClassLoader());
        this.outBoundLeg = in.readParcelable(FlightLeg.class.getClassLoader());
    }

    public double getPrice() {
        return price;
    }

    public boolean isDirectRoute() {
        return directRoute;
    }

    public FlightLeg getInBoundLeg() {
        return inBoundLeg;
    }

    public FlightLeg getOutBoundLeg() {
        return outBoundLeg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.price);
        dest.writeByte(this.directRoute ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.inBoundLeg, flags);
        dest.writeParcelable(this.outBoundLeg, flags);
    }
}

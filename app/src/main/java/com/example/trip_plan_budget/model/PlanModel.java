package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trip_plan_budget.enumeration.TransportType;

public class PlanModel implements Parcelable {
    public static final Parcelable.Creator<PlanModel> CREATOR = new Parcelable.Creator<PlanModel>() {
        @Override
        public PlanModel createFromParcel(Parcel source) {
            return new PlanModel(source);
        }

        @Override
        public PlanModel[] newArray(int size) {
            return new PlanModel[size];
        }
    };
    private String to, from;
    private String dates;
    private double price;
    private TransportType type;

    public PlanModel() {
    }

    public PlanModel(String to, String from, String dates, double price, TransportType type) {
        this.to = to;
        this.from = from;
        this.dates = dates;
        this.price = price;
        this.type = type;
    }

    protected PlanModel(Parcel in) {
        this.to = in.readString();
        this.from = in.readString();
        this.dates = in.readString();
        this.price = in.readDouble();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : TransportType.values()[tmpType];
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.to);
        dest.writeString(this.from);
        dest.writeString(this.dates);
        dest.writeDouble(this.price);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }
}

package com.example.trip_plan_budget.model.flight;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Carrier implements Parcelable {
    public static final Creator<Carrier> CREATOR = new Creator<Carrier>() {
        @Override
        public Carrier createFromParcel(Parcel source) {
            return new Carrier(source);
        }

        @Override
        public Carrier[] newArray(int size) {
            return new Carrier[size];
        }
    };
    @SerializedName("CarrierId")
    private int id;
    @SerializedName("Name")
    private String name;

    public Carrier() {
    }

    protected Carrier(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }
}

package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AirportModel implements Parcelable {
    public static final Parcelable.Creator<AirportModel> CREATOR = new Parcelable.Creator<AirportModel>() {
        @Override
        public AirportModel createFromParcel(Parcel source) {
            return new AirportModel(source);
        }

        @Override
        public AirportModel[] newArray(int size) {
            return new AirportModel[size];
        }
    };
    private String IATA;
    private String city;

    public AirportModel(String IATA, String city) {
        this.IATA = IATA;
        this.city = city;
    }

    protected AirportModel(Parcel in) {
        this.IATA = in.readString();
        this.city = in.readString();
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.IATA);
        dest.writeString(this.city);
    }
}

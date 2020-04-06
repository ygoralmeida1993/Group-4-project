package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

import ir.mirrajabi.searchdialog.core.Searchable;

public class AirportModel implements Parcelable, Searchable {

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
    private String CityId;
    private String CountryId;
    private String CountryName;
    private String PlaceId;
    private String PlaceName;

    public AirportModel() {
    }

    protected AirportModel(Parcel in) {
        this.CityId = in.readString();
        this.CountryId = in.readString();
        this.CountryName = in.readString();
        this.PlaceId = in.readString();
        this.PlaceName = in.readString();
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(String placeId) {
        PlaceId = placeId;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CityId);
        dest.writeString(this.CountryId);
        dest.writeString(this.CountryName);
        dest.writeString(this.PlaceId);
        dest.writeString(this.PlaceName);
    }

    @Override
    public String getTitle() {
        return getPlaceName() + ", " + getPlaceId().split("-")[0];
    }
}

package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaceDetailsModel implements Parcelable {
    public static final Creator<PlaceDetailsModel> CREATOR = new Creator<PlaceDetailsModel>() {
        @Override
        public PlaceDetailsModel createFromParcel(Parcel in) {
            return new PlaceDetailsModel(in);
        }

        @Override
        public PlaceDetailsModel[] newArray(int size) {
            return new PlaceDetailsModel[size];
        }
    };
    private String placeName;
    private String city;
    private int budget;
    private String longitude;
    private String province;
    private String latitude;
    private String place_type;
    private String image;
    private String placeId;

    public PlaceDetailsModel() {
    }

    public PlaceDetailsModel(int budget, String city, String img, String latitude, String longitude, String placeId, String placename, String place_type, String province) {
        this.placeName = placename;
        this.city = city;
        this.budget = budget;
        this.longitude = longitude;
        this.latitude = latitude;
        this.place_type = place_type;
        this.image = img;
        this.placeId = placeId;
        this.province = province;
    }

    protected PlaceDetailsModel(Parcel in) {
        placeName = in.readString();
        city = in.readString();
        budget = in.readInt();
        longitude = in.readString();
        latitude = in.readString();
        place_type = in.readString();
        image = in.readString();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeString(city);
        dest.writeInt(budget);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(place_type);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPlacename() {
        return placeName;
    }

    public void setPlacename(String placename) {
        this.placeName = placename;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPlace_type() {
        return place_type;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }
}

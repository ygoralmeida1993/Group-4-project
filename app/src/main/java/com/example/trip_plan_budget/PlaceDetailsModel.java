package com.example.trip_plan_budget;


import android.os.Parcel;
import android.os.Parcelable;

public class PlaceDetailsModel implements Parcelable {
    String placename;
    String city;
    int budget;
    String longitude;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String latitude;
    String place_type;
    String image;

    public PlaceDetailsModel(String placename, String city, int budget, String longitude, String latitude, String place_type,String img) {
        this.placename = placename;
        this.city = city;
        this.budget = budget;
        this.longitude = longitude;
        this.latitude = latitude;
        this.place_type = place_type;
        this.image=img;
    }



    protected PlaceDetailsModel(Parcel in) {
        placename = in.readString();
        city = in.readString();
        budget = in.readInt();
        longitude = in.readString();
        latitude = in.readString();
        place_type = in.readString();
        image=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placename);
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

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
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

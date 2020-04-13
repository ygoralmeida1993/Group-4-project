package com.example.trip_plan_budget.model.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trip_plan_budget.model.hotel.Photo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantModel implements Parcelable {

    public final static Parcelable.Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        public RestaurantModel[] newArray(int size) {
            return (new RestaurantModel[size]);
        }

    };
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("num_reviews")
    @Expose
    private String numReviews;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("address")
    @Expose
    private String address;

    protected RestaurantModel(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
        this.numReviews = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((Photo) in.readValue((Photo.class.getClassLoader())));
        this.distance = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public RestaurantModel() {
    }

    /**
     * @param website
     * @param address
     * @param distance
     * @param phone
     * @param latitude
     * @param name
     * @param numReviews
     * @param rating
     * @param photo
     * @param longitude
     */
    public RestaurantModel(String name, String latitude, String longitude, String numReviews, Photo photo, String distance, String rating, String phone, String website, String address) {
        super();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numReviews = numReviews;
        this.photo = photo;
        this.distance = distance;
        this.rating = rating;
        this.phone = phone;
        this.website = website;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(String numReviews) {
        this.numReviews = numReviews;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(numReviews);
        dest.writeValue(photo);
        dest.writeValue(distance);
        dest.writeValue(rating);
        dest.writeValue(phone);
        dest.writeValue(website);
        dest.writeValue(address);
    }

    public int describeContents() {
        return 0;
    }

}
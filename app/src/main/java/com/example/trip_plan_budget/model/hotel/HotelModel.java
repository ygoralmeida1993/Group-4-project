package com.example.trip_plan_budget.model.hotel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelModel implements Parcelable {

    public final static Parcelable.Creator<HotelModel> CREATOR = new Creator<HotelModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public HotelModel createFromParcel(Parcel in) {
            return new HotelModel(in);
        }

        public HotelModel[] newArray(int size) {
            return (new HotelModel[size]);
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
    @SerializedName("location_string")
    @Expose
    private String locationString;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("price_level")
    @Expose
    private String priceLevel;
    @SerializedName("price")
    @Expose
    private String price;

    protected HotelModel(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
        this.numReviews = ((String) in.readValue((String.class.getClassLoader())));
        this.locationString = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((Photo) in.readValue((Photo.class.getClassLoader())));
        this.distance = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((String) in.readValue((String.class.getClassLoader())));
        this.priceLevel = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public HotelModel() {
    }

    /**
     * @param distance
     * @param price
     * @param latitude
     * @param name
     * @param numReviews
     * @param locationString
     * @param rating
     * @param photo
     * @param priceLevel
     * @param longitude
     */
    public HotelModel(String name, String latitude, String longitude, String numReviews, String locationString, Photo photo, String distance, String rating, String priceLevel, String price) {
        super();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numReviews = numReviews;
        this.locationString = locationString;
        this.photo = photo;
        this.distance = distance;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.price = price;
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

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
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

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(numReviews);
        dest.writeValue(locationString);
        dest.writeValue(photo);
        dest.writeValue(distance);
        dest.writeValue(rating);
        dest.writeValue(priceLevel);
        dest.writeValue(price);
    }

    public int describeContents() {
        return 0;
    }

}





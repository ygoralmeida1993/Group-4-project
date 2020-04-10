package com.example.trip_plan_budget.model.flight;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightPriceListModel implements Parcelable {

    public static final Parcelable.Creator<FlightPriceListModel> CREATOR = new Parcelable.Creator<FlightPriceListModel>() {
        @Override
        public FlightPriceListModel createFromParcel(Parcel source) {
            return new FlightPriceListModel(source);
        }

        @Override
        public FlightPriceListModel[] newArray(int size) {
            return new FlightPriceListModel[size];
        }
    };
    @SerializedName("Quotes")
    private List<PriceModel> prices;
    @SerializedName("Carriers")
    private List<Carrier> carriers;

    public FlightPriceListModel() {
    }

    protected FlightPriceListModel(Parcel in) {
        this.prices = in.createTypedArrayList(PriceModel.CREATOR);
        this.carriers = in.createTypedArrayList(Carrier.CREATOR);
    }

    public List<PriceModel> getPrices() {
        return prices;
    }

    public List<Carrier> getCarriers() {
        return carriers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.prices);
        dest.writeTypedList(this.carriers);
    }
}


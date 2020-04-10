package com.example.trip_plan_budget.model.flight;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

class Carrier implements Parcelable {
    public static final Parcelable.Creator<Carrier> CREATOR = new Parcelable.Creator<Carrier>() {
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

class PriceModel implements Parcelable {
    public static final Parcelable.Creator<PriceModel> CREATOR = new Parcelable.Creator<PriceModel>() {
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

class FlightLeg implements Parcelable {

    public static final Parcelable.Creator<FlightLeg> CREATOR = new Parcelable.Creator<FlightLeg>() {
        @Override
        public FlightLeg createFromParcel(Parcel source) {
            return new FlightLeg(source);
        }

        @Override
        public FlightLeg[] newArray(int size) {
            return new FlightLeg[size];
        }
    };
    @SerializedName("CarrierIds")
    private List<Integer> carriers;
    @SerializedName("OriginId")
    private int originID;
    @SerializedName("DestinationId")
    private int destID;
    @SerializedName("DepartureDate")
    private String deptDate;

    public FlightLeg() {
    }

    protected FlightLeg(Parcel in) {
        this.carriers = new ArrayList<Integer>();
        in.readList(this.carriers, Integer.class.getClassLoader());
        this.originID = in.readInt();
        this.destID = in.readInt();
        this.deptDate = in.readString();
    }

    public List<Integer> getCarriers() {
        return carriers;
    }

    public int getOriginID() {
        return originID;
    }

    public int getDestID() {
        return destID;
    }

    public String getDeptDate() {
        return deptDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.carriers);
        dest.writeInt(this.originID);
        dest.writeInt(this.destID);
        dest.writeString(this.deptDate);
    }
}

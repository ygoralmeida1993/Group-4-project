package com.example.trip_plan_budget.model.flight;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trip_plan_budget.enumeration.FlightClass;

import java.util.List;

public class FlightModel implements Parcelable {


    public static final Parcelable.Creator<FlightModel> CREATOR = new Parcelable.Creator<FlightModel>() {
        @Override
        public FlightModel createFromParcel(Parcel source) {
            return new FlightModel(source);
        }

        @Override
        public FlightModel[] newArray(int size) {
            return new FlightModel[size];
        }
    };
    private AirportModel departure, landing;
    private boolean roundTrip;
    private FlightClass flightClass;
    private String to, from;
    private List<Carrier> carriers;
    private PriceModel priceModel;
    private int adults, infants, children;
    private String type = "flight";

    public FlightModel() {
    }

    public FlightModel(AirportModel departure, AirportModel landing, boolean roundTrip, int adults, int infants, int children, FlightClass flightClass) {
        this.departure = departure;
        this.landing = landing;
        this.roundTrip = roundTrip;
        this.adults = adults;
        this.infants = infants;
        this.children = children;
        this.flightClass = flightClass;
    }

    protected FlightModel(Parcel in) {
        this.departure = in.readParcelable(AirportModel.class.getClassLoader());
        this.landing = in.readParcelable(AirportModel.class.getClassLoader());
        this.roundTrip = in.readByte() != 0;
        int tmpFlightClass = in.readInt();
        this.flightClass = tmpFlightClass == -1 ? null : FlightClass.values()[tmpFlightClass];
        this.to = in.readString();
        this.from = in.readString();
        this.carriers = in.createTypedArrayList(Carrier.CREATOR);
        this.priceModel = in.readParcelable(PriceModel.class.getClassLoader());
        this.adults = in.readInt();
        this.infants = in.readInt();
        this.children = in.readInt();
        this.type = in.readString();
    }

    public String getType() {
        return type;
    }

    public List<Carrier> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }

    public PriceModel getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(PriceModel priceModel) {
        this.priceModel = priceModel;
    }

    public AirportModel getDeparture() {
        return departure;
    }

    public void setDeparture(AirportModel departure) {
        this.departure = departure;
    }

    public AirportModel getLanding() {
        return landing;
    }

    public void setLanding(AirportModel landing) {
        this.landing = landing;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getInfants() {
        return infants;
    }

    public void setInfants(int infants) {
        this.infants = infants;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public FlightClass getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.departure, flags);
        dest.writeParcelable(this.landing, flags);
        dest.writeByte(this.roundTrip ? (byte) 1 : (byte) 0);
        dest.writeInt(this.flightClass == null ? -1 : this.flightClass.ordinal());
        dest.writeString(this.to);
        dest.writeString(this.from);
        dest.writeTypedList(this.carriers);
        dest.writeParcelable(this.priceModel, flags);
        dest.writeInt(this.adults);
        dest.writeInt(this.infants);
        dest.writeInt(this.children);
        dest.writeString(this.type);
    }
}

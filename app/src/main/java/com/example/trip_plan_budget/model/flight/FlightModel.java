package com.example.trip_plan_budget.model.flight;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trip_plan_budget.enumeration.FlightClass;
import com.example.trip_plan_budget.enumeration.PassengerType;

import java.util.HashMap;
import java.util.List;

public class FlightModel implements Parcelable {

    private AirportModel departure, landing;

    private boolean roundTrip;
    private HashMap<PassengerType, List<FlightPassengerModel>> passengers;
    private FlightClass flightClass;
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
    private String to, from;

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

    public HashMap<PassengerType, List<FlightPassengerModel>> getPassengers() {
        return passengers;
    }

    public void setPassengers(HashMap<PassengerType, List<FlightPassengerModel>> passengers) {
        this.passengers = passengers;
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

    private int adults, infants, children;

    protected FlightModel(Parcel in) {
        this.departure = in.readParcelable(AirportModel.class.getClassLoader());
        this.landing = in.readParcelable(AirportModel.class.getClassLoader());
        this.roundTrip = in.readByte() != 0;
        this.passengers = (HashMap<PassengerType, List<FlightPassengerModel>>) in.readSerializable();
        int tmpFlightClass = in.readInt();
        this.flightClass = tmpFlightClass == -1 ? null : FlightClass.values()[tmpFlightClass];
        this.to = in.readString();
        this.from = in.readString();
        this.adults = in.readInt();
        this.infants = in.readInt();
        this.children = in.readInt();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.departure, flags);
        dest.writeParcelable(this.landing, flags);
        dest.writeByte(this.roundTrip ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.passengers);
        dest.writeInt(this.flightClass == null ? -1 : this.flightClass.ordinal());
        dest.writeString(this.to);
        dest.writeString(this.from);
        dest.writeInt(this.adults);
        dest.writeInt(this.infants);
        dest.writeInt(this.children);
    }
}

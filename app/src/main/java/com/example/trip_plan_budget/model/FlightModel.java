package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trip_plan_budget.enumeration.FlightClass;
import com.example.trip_plan_budget.enumeration.PassengerType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FlightModel implements Parcelable {

    private AirportModel departure, landing;
    public static final Creator<FlightModel> CREATOR = new Creator<FlightModel>() {
        @Override
        public FlightModel createFromParcel(Parcel in) {
            return new FlightModel(in);
        }

        @Override
        public FlightModel[] newArray(int size) {
            return new FlightModel[size];
        }
    };
    private boolean roundTrip;
    private HashMap<PassengerType, List<FlightPassengerModel>> passengers;
    private FlightClass flightClass;
    private Date to, from;

    public FlightModel() {
    }

    private int adults, infants, children;

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
        departure = in.readParcelable(AirportModel.class.getClassLoader());
        landing = in.readParcelable(AirportModel.class.getClassLoader());
        roundTrip = in.readByte() != 0;
        adults = in.readInt();
        infants = in.readInt();
        children = in.readInt();
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

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(departure, flags);
        dest.writeParcelable(landing, flags);
        dest.writeByte((byte) (roundTrip ? 1 : 0));
        dest.writeInt(adults);
        dest.writeInt(infants);
        dest.writeInt(children);
    }
}

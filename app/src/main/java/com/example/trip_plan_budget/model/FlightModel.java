package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trip_plan_budget.enumeration.FlightClass;

import java.util.Date;
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
    private List<FlightPassengerModel> passengers;
    private boolean roundTrip;
    private FlightClass flightClass;
    private Date to, from;

    public FlightModel() {
    }

    public FlightModel(AirportModel departure, AirportModel landing, boolean roundTrip, FlightClass flightClass) {
        this.departure = departure;
        this.landing = landing;
        this.roundTrip = roundTrip;
        this.flightClass = flightClass;
    }

    protected FlightModel(Parcel in) {
        this.departure = in.readParcelable(AirportModel.class.getClassLoader());
        this.landing = in.readParcelable(AirportModel.class.getClassLoader());
        this.passengers = in.createTypedArrayList(FlightPassengerModel.CREATOR);
        this.roundTrip = in.readByte() != 0;
        int tmpFlightClass = in.readInt();
        this.flightClass = tmpFlightClass == -1 ? null : FlightClass.values()[tmpFlightClass];
        long tmpTo = in.readLong();
        this.to = tmpTo == -1 ? null : new Date(tmpTo);
        long tmpFrom = in.readLong();
        this.from = tmpFrom == -1 ? null : new Date(tmpFrom);
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

    public List<FlightPassengerModel> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<FlightPassengerModel> passengers) {
        this.passengers = passengers;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
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
        dest.writeParcelable(this.departure, flags);
        dest.writeParcelable(this.landing, flags);
        dest.writeTypedList(this.passengers);
        dest.writeByte(this.roundTrip ? (byte) 1 : (byte) 0);
        dest.writeInt(this.flightClass == null ? -1 : this.flightClass.ordinal());
        dest.writeLong(this.to != null ? this.to.getTime() : -1);
        dest.writeLong(this.from != null ? this.from.getTime() : -1);
    }
}

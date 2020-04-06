package com.example.trip_plan_budget.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trip_plan_budget.enumeration.Gender;

public class FlightPassengerModel implements Parcelable {
    public static final Parcelable.Creator<FlightPassengerModel> CREATOR = new Parcelable.Creator<FlightPassengerModel>() {
        @Override
        public FlightPassengerModel createFromParcel(Parcel source) {
            return new FlightPassengerModel(source);
        }

        @Override
        public FlightPassengerModel[] newArray(int size) {
            return new FlightPassengerModel[size];
        }
    };
    private String name;
    private int age;
    private Gender gender;

    public FlightPassengerModel(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    protected FlightPassengerModel(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : Gender.values()[tmpGender];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
    }
}

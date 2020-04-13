package com.example.trip_plan_budget.model.main;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserModel implements Parcelable {
    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
    private String email;
    private List<PlanModel> plans;

    public UserModel(String email, List<PlanModel> plans) {
        this.email = email;
        this.plans = plans;
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.email = in.readString();
        this.plans = in.createTypedArrayList(PlanModel.CREATOR);
    }

    public List<PlanModel> getPlans() {
        return plans;
    }

    public void setPlans(List<PlanModel> plans) {
        this.plans = plans;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeTypedList(this.plans);
    }
}

package com.example.trip_plan_budget;


public class CarMileageModel {
    String carType;

    public CarMileageModel(String carType, String carBrand, String carMake, double mileage) {
        this.carType = carType;
        this.carBrand = carBrand;
        this.carMake = carMake;
        this.mileage = mileage;
    }
public CarMileageModel(){

}
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    String carBrand;
    String carMake;
    double mileage;
}

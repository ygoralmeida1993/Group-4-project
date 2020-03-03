package com.example.trip_plan_budget;

public class GasApiModel {
    public GasApiModel() {
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getGasolinePrice() {
        return gasolinePrice;
    }

    public void setGasolinePrice(String gasolinePrice) {
        this.gasolinePrice = gasolinePrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    String provinceName;

    public GasApiModel(String provinceName, String gasolinePrice, String currency) {
        this.provinceName = provinceName;
        this.gasolinePrice = gasolinePrice;
        this.currency = currency;
    }

    String gasolinePrice;
    String currency;



}

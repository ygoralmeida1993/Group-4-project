package com.example.trip_plan_budget.model;

public class GasApiModel {
    private String provinceName;
    private String gasolinePrice;
    private String currency;

    public GasApiModel() {
    }

    public GasApiModel(String provinceName, String gasolinePrice, String currency) {
        this.provinceName = provinceName;
        this.gasolinePrice = gasolinePrice;
        this.currency = currency;
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


}

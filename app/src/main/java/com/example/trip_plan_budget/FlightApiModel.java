package com.example.trip_plan_budget;

public class FlightApiModel {
    private String Quotes;
    private String Places;
    private String Carriers;

    public FlightApiModel() {
}

    public String Quotes() {
        return Quotes();
    }

    public void setQutes(String Quotes) {
        this.Quotes = Quotes;
    }

    public String Places() {
        return Places;
    }

    public void setPlaces(String Places) {
        this.Places = Places;
    }

    public String getCurrencies() {
        return Currencies;
    }

    public void setCurrencies(String Currencies) {
        this.Currencies = Currencies;
    }

    public  String Carriers(){
        return  Carriers();
    }
    public void setCarriers(String Carriers){
        this.Carriers=Carriers;
    }

    public FlightApiModel(String provinceName, String Places, String Currencies) {
        this.Quotes = Quotes;
        this.Carriers = Carriers;
        this.Places = Places;
        this.Currencies = Currencies;
    }


    String Currencies;

}

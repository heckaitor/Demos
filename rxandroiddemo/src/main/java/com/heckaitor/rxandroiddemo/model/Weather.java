package com.heckaitor.rxandroiddemo.model;

/**
 * Created by kaige1 on 2016/11/14.
 */
public class Weather {

    private String mCity;
    private String mDate;
    private String mStatus;
    private String mTemperature;

    public Weather() { }

    public String getCity() {
        return mCity;
    }

    public void setCity(String City) {
        this.mCity = City;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String Date) {
        this.mDate = Date;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String Status) {
        this.mStatus = Status;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String Temperature) {
        this.mTemperature = Temperature;
    }

    @Override
    public String toString() {
        return mDate + " " + mCity + ": " + mTemperature + ", " + mStatus;
    }
}

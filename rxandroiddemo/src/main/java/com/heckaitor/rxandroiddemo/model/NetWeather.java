package com.heckaitor.rxandroiddemo.model;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kaige1 on 2016/11/14.
 */
public class NetWeather implements IWeather {

    @Override
    public Weather getWeather(String city) throws Exception {
        if (TextUtils.isEmpty(city)) {
            throw new NullPointerException("A city name must be specified!");
        }

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            throw e;
        }

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setDate(getToday());
        weather.setTemperature("10℃");
        weather.setStatus("very Nice!");

        return weather;
    }

    private String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }
}

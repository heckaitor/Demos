package com.heckaitor.rxandroiddemo.model;

/**
 * Created by kaige1 on 2016/11/14.
 */
public class WeatherRepo {

    private IWeather netWeather;

    private WeatherRepo() {
        netWeather = new NetWeather();
    }

    private volatile static WeatherRepo sInstance = null;
    public static WeatherRepo getInstance() {
        if (sInstance == null) {
            synchronized (WeatherRepo.class) {
                if (sInstance == null) {
                    sInstance = new WeatherRepo();
                }
            }
        }

        return sInstance;
    }

    public Weather getNetWeather(String city) throws Exception {
        return netWeather.getWeather(city);
    }

}

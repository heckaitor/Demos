package com.heckaitor.rxandroiddemo.model;

/**
 * Created by kaige1 on 2016/11/14.
 */
public interface IWeather {

    Weather getWeather(String city)throws Exception;
}

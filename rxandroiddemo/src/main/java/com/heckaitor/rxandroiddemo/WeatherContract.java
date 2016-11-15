package com.heckaitor.rxandroiddemo;

/**
 * Created by kaige1 on 2016/11/14.
 */
public interface WeatherContract {

    interface IView {

        void showResult(String result);

        void showLoading();

        void dismissLoading();

    }

    interface IPresent {

        void searchWeather(String city);
    }

}

package com.heckaitor.rxandroiddemo;

import com.heckaitor.rxandroiddemo.model.Weather;
import com.heckaitor.rxandroiddemo.model.WeatherRepo;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kaige1 on 2016/11/14.
 */
public class WeatherPresent implements WeatherContract.IPresent {

    private WeatherContract.IView mView;

    private WeatherRepo mRepo;

    public WeatherPresent(WeatherContract.IView view) {
        this.mView = view;
    }

    @Override
    public void searchWeather(final String city) {
        Observable.create(new Observable.OnSubscribe<Weather>() {

            @Override
            public void call(Subscriber<? super Weather> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onStart();
                        final Weather result = WeatherRepo.getInstance().getNetWeather(city);
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {

                    @Override
                    public void onStart() {
                        mView.showResult(null);
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showResult(e != null ? e.getMessage() : "");
                        mView.dismissLoading();
                    }

                    @Override
                    public void onNext(Weather weather) {
                        mView.showResult(weather.toString());
                    }
                });

    }

}

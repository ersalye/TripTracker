package com.antonchaynikov.triptracker.mainscreen;

import android.location.Location;

import com.antonchaynikov.triptracker.data.location.LocationSource;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


class MockLocationSource implements LocationSource {

    private List<Location> mLocations;
    private PublishSubject<Location> mLocationsObservable = PublishSubject.create();
    private PublishSubject<Boolean> mGeolocationAvailabilityObservable = PublishSubject.create();

    MockLocationSource(@NonNull List<Location> locations) {
        mLocations = locations;
    }

    @Override
    public void startUpdates() {
        for (Location location: mLocations) {
            mLocationsObservable.onNext(location);
        }
    }

    @Override
    public void finishUpdates() {

    }

    @Override
    public Observable<Location> getLocationsObservable() {
        return mLocationsObservable;
    }

    @Override
    public Observable<Boolean> getGeolocationAvailabilityObservable() {
        return mGeolocationAvailabilityObservable;
    }

    void onGeolocationAvailabilityChanged(boolean isAvailable) {
        mGeolocationAvailabilityObservable.onNext(isAvailable);
    }
}

package com.antonchaynikov.tripscreen;

import android.location.Location;

import androidx.annotation.NonNull;

import com.antonchaynikov.core.data.location.LocationSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MockLocationSource implements LocationSource {

    private List<Location> mLocations;
    private PublishSubject<Location> mLocationsObservable = PublishSubject.create();
    private PublishSubject<Boolean> mGeolocationAvailabilityObservable = PublishSubject.create();

    public MockLocationSource(@NonNull List<Location> locations) {
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

    public void onGeolocationAvailabilityChanged(boolean isAvailable) {
        mGeolocationAvailabilityObservable.onNext(isAvailable);
    }
}

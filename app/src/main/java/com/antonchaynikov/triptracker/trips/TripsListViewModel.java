package com.antonchaynikov.triptracker.trips;

import android.util.Log;

import com.antonchaynikov.triptracker.data.model.Trip;
import com.antonchaynikov.triptracker.data.repository.Repository;
import com.antonchaynikov.triptracker.viewmodel.BasicViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

class TripsListViewModel extends BasicViewModel {

    private PublishSubject<Boolean> mShowEmptyListMessageEvent = PublishSubject.create();
    private PublishSubject<List<Trip>> mTripsListSubject = PublishSubject.create();

    private Repository mRepository;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();

    TripsListViewModel(@NonNull Repository repository) {
        mRepository = repository;
    }

    void onStart() {
        Log.d(TripsListViewModel.class.getCanonicalName(), "onStart");
        mShowProgressBarEventBroadcast.onNext(true);
        mSubscriptions.add(mRepository
                .getAllTrips()
                .subscribe(this::onTripsLoaded));
    }

    Observable<Boolean> getEmptyListEventObservable() {
        return mShowEmptyListMessageEvent;
    }

    Observable<List<Trip>> getTripListObservable() {
        return mTripsListSubject;
    }

    private void onTripsLoaded(@NonNull List<Trip> trips) {
        if (trips.isEmpty()) {
            mShowEmptyListMessageEvent.onNext(true);
        }
        mTripsListSubject.onNext(trips);
        mShowProgressBarEventBroadcast.onNext(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mSubscriptions.dispose();
    }
}

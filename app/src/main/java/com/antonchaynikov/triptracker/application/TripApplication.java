package com.antonchaynikov.triptracker.application;

import com.antonchaynikov.triptracker.authentication.AuthModule;
import com.antonchaynikov.triptracker.data.location.LocationSourceModule;
import com.antonchaynikov.triptracker.data.tripmanager.TripManagerModule;
import com.antonchaynikov.triptracker.history.DaggerHistoryComponent;
import com.antonchaynikov.triptracker.history.HistoryActivity;
import com.antonchaynikov.triptracker.history.HistoryFragment;
import com.antonchaynikov.triptracker.history.HistoryModule;

import com.antonchaynikov.triptracker.mainscreen.DaggerTripComponent;
import com.antonchaynikov.triptracker.mainscreen.TripActivity;

import com.antonchaynikov.triptracker.mainscreen.TripFragment;
import com.antonchaynikov.triptracker.mainscreen.TripModule;
import com.antonchaynikov.triptracker.trips.DaggerTripsListComponent;
import com.antonchaynikov.triptracker.trips.TripsListActivity;
import com.antonchaynikov.triptracker.trips.TripsListFragment;
import com.antonchaynikov.triptracker.trips.TripsListModule;
import com.antonchaynikov.triptracker.viewmodel.CommonViewModelModule;

import androidx.multidex.MultiDexApplication;

public class TripApplication extends MultiDexApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public void injectTripsListFragmentDependencies(TripsListFragment fragment) {
       DaggerTripsListComponent.builder()
                .appComponent(mAppComponent)
                .tripsListModule(new TripsListModule(fragment))
                .build()
                .inject(fragment);
    }

    public void injectHistoryFragmentDependencies(HistoryFragment fragment, long tripStartDate) {
       DaggerHistoryComponent.builder()
                .appComponent(mAppComponent)
                .historyModule(new HistoryModule(fragment, tripStartDate))
                .commonViewModelModule(new CommonViewModelModule())
                .build()
                .inject(fragment);
    }

    public void injectTripFragmentDependencies(TripFragment fragment, boolean isLocationPermissionGranted) {
        DaggerTripComponent.builder()
                .appComponent(mAppComponent)
                .authModule(new AuthModule())
                .commonViewModelModule(new CommonViewModelModule())
                .locationSourceModule(new LocationSourceModule())
                .tripManagerModule(new TripManagerModule())
                .tripModule(new TripModule(fragment, isLocationPermissionGranted))
                .build()
                .inject(fragment);
    }
}

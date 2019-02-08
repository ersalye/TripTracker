package com.antonchaynikov.triptracker.mainscreen;

import com.antonchaynikov.triptracker.viewmodel.TripStatistics;

import org.junit.Test;

import static org.junit.Assert.*;

public class TripStatisticsTest {

    @Test
    public void getDefaultStatistics() {
        TripStatistics tripStatistics = TripStatistics.getDefaultStatistics();
        assertEquals("0", tripStatistics.getDistance());
        assertEquals("0", tripStatistics.getSpeed());
    }
}
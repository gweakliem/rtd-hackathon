package net.eightytwenty.rtd.model;

import net.eightytwenty.rtd.gtfs.Stop;
import net.eightytwenty.rtd.gtfs.StopTime;
import net.eightytwenty.rtd.gtfs.Trip;

/**
 * A StopTime defines when a vehicle arrives at a location, how long it stays there, and when it departs.
 * StopTimes define the path and schedule of Trips.
 */
public class StopTimeModel {
    private StopTime stopTime;
    private Stop stop;
    private Trip trip;


    public StopTimeModel(StopTime stopTime, Stop stop, Trip trip) {
        this.stopTime = stopTime;
        this.stop = stop;
        this.trip = trip;
    }

    public StopTime getStopTime() {
        return stopTime;
    }

    public Stop getStop() {
        return stop;
    }

    public Trip getTrip() {
        return trip;
    }
}

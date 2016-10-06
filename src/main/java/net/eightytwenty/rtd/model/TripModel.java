package net.eightytwenty.rtd.model;

import net.eightytwenty.rtd.gtfs.Calendar;
import net.eightytwenty.rtd.gtfs.Route;
import net.eightytwenty.rtd.gtfs.Trip;

/**
 * Expresses the relationship between trip and related
 * GTFS objects
 */
public class TripModel {
    private Trip trip;
    private Route route;
    private Calendar calendar;


    public TripModel(Trip trip, Route route, Calendar calendar) {
        this.trip = trip;
        this.route = route;
        this.calendar = calendar;
    }

    public Trip getTrip() {
        return trip;
    }

    public Route getRoute() {
        return route;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}

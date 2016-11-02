package net.eightytwenty.rtd.model;

import net.eightytwenty.rtd.gtfs.Calendar;
import net.eightytwenty.rtd.gtfs.Route;
import net.eightytwenty.rtd.gtfs.StopTime;
import net.eightytwenty.rtd.gtfs.Trip;

import java.util.List;

/**
 * A Trip represents a journey taken by a vehicle through Stops. Trips are time-specific — they are defined as a
 * sequence of StopTimes, so a single Trip represents one journey along a transit line or route. In addition to
 * StopTimes, Trips use Calendars to define the days when a Trip is available to passengers.
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

    public List<StopTime> getStopTimes() {
        return null;
    }

}

package net.eightytwenty;

import java.util.List;

public class StopInfo {
    private String routeNumber;
    private List<Long> nextArrivals;
    private String stopId;

    public StopInfo(String routeNumber, String stopId, List<Long> nextArrivals) {
        this.routeNumber = routeNumber;
        this.nextArrivals = nextArrivals;
        this.stopId = stopId;
    }

    public List<Long> getNextArrivals() {
        return nextArrivals;
    }

    public String getStopId() {
        return stopId;
    }

    public String getRouteNumber() {
        return routeNumber;
    }
}

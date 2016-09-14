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
}

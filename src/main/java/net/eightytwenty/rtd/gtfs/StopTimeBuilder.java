package net.eightytwenty.rtd.gtfs;

import java.time.Duration;

public class StopTimeBuilder {
    private Duration arrivalTime;
    private String tripId;
    private Duration departureTime;
    private String stopHeadsign;
    private StopTime.RideArrangement pickupType;
    private StopTime.RideArrangement dropOffType;
    private double shapeDistTRavelred;
    private String stopId;
    private int stopSequence;
    private boolean isTimeExact;

    private StopTimeBuilder() {

    }

    public StopTimeBuilder arrivalTime(Duration arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public StopTimeBuilder tripId(String tripId) {
        this.tripId = tripId;
        return this;
    }

    public StopTimeBuilder departureTime(Duration departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public StopTimeBuilder stopHeadsign(String stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
        return this;
    }

    public StopTimeBuilder pickupType(StopTime.RideArrangement pickupType) {
        this.pickupType = pickupType;
        return this;
    }

    public StopTimeBuilder dropOffType(StopTime.RideArrangement dropOffType) {
        this.dropOffType = dropOffType;
        return this;
    }

    public StopTimeBuilder shapeDistTRavelred(double shapeDistTRavelred) {
        this.shapeDistTRavelred = shapeDistTRavelred;
        return this;
    }

    public StopTimeBuilder stopId(String stopId) {
        this.stopId = stopId;
        return this;
    }

    public StopTimeBuilder stopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
        return this;
    }

    public StopTimeBuilder timeExact(boolean timeExact) {
        isTimeExact = timeExact;
        return this;
    }

    public static StopTimeBuilder stopTimeBuilder() {
        return new StopTimeBuilder();
    }

    public StopTime build() {
        return new StopTime(tripId, arrivalTime,
                departureTime, stopId, stopSequence,
                stopHeadsign, pickupType, dropOffType,
                shapeDistTRavelred, isTimeExact);
    }
}

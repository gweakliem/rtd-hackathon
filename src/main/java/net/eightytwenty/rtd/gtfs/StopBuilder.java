package net.eightytwenty.rtd.gtfs;

import java.util.TimeZone;

/**
 * Created by gordon on 10/2/16.
 */
public class StopBuilder {
    private double stopLat;
    private boolean wheelchairBoarding;
    private String stopCode;
    private double stopLon;
    private TimeZone stopTimezone;
    private String stopUrl;
    private String parentStation;
    private String stopDesc;
    private String stopName;
    private boolean isStation;
    private String stopId;
    private String zoneId;

    public StopBuilder stopLat(double stopLat) {
        this.stopLat = stopLat;
        return this;
    }

    public StopBuilder wheelchairBoarding(boolean wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
        return this;
    }

    public StopBuilder stopCode(String stopCode) {
        this.stopCode = stopCode;
        return this;
    }

    public StopBuilder stopLon(double stopLon) {
        this.stopLon = stopLon;
        return this;
    }

    public StopBuilder stopTimezone(TimeZone stopTimezone) {
        this.stopTimezone = stopTimezone;
        return this;
    }

    public StopBuilder stopUrl(String stopUrl) {
        this.stopUrl = stopUrl;
        return this;
    }

    public StopBuilder parentStation(String parentStation) {
        this.parentStation = parentStation;
        return this;
    }

    public StopBuilder stopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
        return this;
    }

    public StopBuilder stopName(String stopName) {
        this.stopName = stopName;
        return this;
    }

    public StopBuilder station(boolean station) {
        isStation = station;
        return this;
    }

    public StopBuilder stopId(String stopId) {
        this.stopId = stopId;
        return this;
    }

    public StopBuilder zoneId(String zoneId) {
        this.zoneId = zoneId;
        return this;
    }


    private StopBuilder() {

    }

    public static StopBuilder stopBuilder() {
        return new StopBuilder();
    }

    public Stop build() {
        return new Stop(
                stopLat,
                wheelchairBoarding,
                stopCode,
                stopLon,
                stopTimezone,
                stopUrl,
                parentStation,
                stopDesc,
                stopName,
                isStation,
                stopId,
                zoneId);
    }

}

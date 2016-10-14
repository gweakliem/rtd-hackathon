package net.eightytwenty.rtd.gtfs;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static net.eightytwenty.rtd.gtfs.GtfsHelper.tryGet;

public class StopTime {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    public static final String HEADER =
            "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled,timepoint";

    private String tripId;
    // stored as the time from midnight, because GTFS can specify times through the next day, which they'll
    // encode with an hour > 23. i.e. 24:30 is half past midnight the following day. So we'll store the time from
    // midnight on the schedule date and calculate actual schedule dates with that offset.
    private Duration arrivalTime;
    private Duration departureTime;
    private String stopId;
    private int stopSequence;
    private String stopHeadsign;
    private RideArrangement pickupType;
    private RideArrangement dropOffType;
    private double shapeDistTraveled;
    private boolean isTimeExact;

    public StopTime(String tripId, Duration arrivalTime, Duration departureTime, String stopId, int stopSequence, String stopHeadsign, RideArrangement pickupType, RideArrangement dropOffType, double shapeDistTraveled, boolean isTimeExact) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
        this.stopHeadsign = stopHeadsign;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;
        this.shapeDistTraveled = shapeDistTraveled;
        this.isTimeExact = isTimeExact;
    }

    public enum RideArrangement {
        REGULARLY_SCHEDULED(0), // Regularly scheduled drop off
        NOT_AVAILABLE(1), // No drop off available
        MUST_PHONE_AGENCY(2), // Must phone agency to arrange drop off
        COORDINATE_WITH_DRIVER(3);

        private final int numericValue;

        public static final Map<String, RideArrangement> ORDINAL_MAP =
                Collections.unmodifiableMap(new HashMap<String, RideArrangement>() {
                    {
                        put("0", REGULARLY_SCHEDULED);
                        put("1", NOT_AVAILABLE);
                        put("2", MUST_PHONE_AGENCY);
                        put("3", COORDINATE_WITH_DRIVER);
                    }
                });

        RideArrangement(int intValue) {
            numericValue = intValue;
        }

        private static RideArrangement parseRideArrangement(String ordinalValue) {
            String deref = Optional.ofNullable(ordinalValue).orElse("0");
            return ORDINAL_MAP.get(deref);
        }
    }

    public static StopTime[] parse(InputStream input) throws IOException {
        return GtfsHelper.parseHelper(input,
                StopTime.class,
                header -> header.startsWith(HEADER),
                fields -> new StopTime(
                        fields[0],
                        parseDuration(fields[1]),
                        parseDuration(fields[2]),
                        fields[3],
                        Integer.parseInt(tryGet(fields, 4)),
                        tryGet(fields, 5),
                        RideArrangement.parseRideArrangement(tryGet(fields, 6, "0")),
                        RideArrangement.parseRideArrangement(tryGet(fields, 7, "0")),
                        Double.parseDouble(tryGet(fields, 8, "-1.0")),
                        tryGet(fields, 9, "1").equals(1)
                ));
    }

    private static Duration parseDuration(String field) {
        String[] fields = field.split(":");

        Duration result = Duration
                .ofHours(Integer.parseInt(fields[0]))
                .plusMinutes(Integer.parseInt(fields[1]))
                .plusSeconds(Integer.parseInt(fields[2]));
        return result;
    }


    public String getTripId() {
        return tripId;
    }

    public Duration getArrivalTime() {
        return arrivalTime;
    }

    public Duration getDepartureTime() {
        return departureTime;
    }

    public String getStopId() {
        return stopId;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public String getStopHeadsign() {
        return stopHeadsign;
    }

    public RideArrangement getPickupType() {
        return pickupType;
    }

    public RideArrangement getDropOffType() {
        return dropOffType;
    }

    public double getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public boolean isTimeExact() {
        return isTimeExact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopTime stopTime = (StopTime) o;
        return stopSequence == stopTime.stopSequence &&
                Double.compare(stopTime.shapeDistTraveled, shapeDistTraveled) == 0 &&
                isTimeExact == stopTime.isTimeExact &&
                Objects.equals(tripId, stopTime.tripId) &&
                Objects.equals(arrivalTime, stopTime.arrivalTime) &&
                Objects.equals(departureTime, stopTime.departureTime) &&
                Objects.equals(stopId, stopTime.stopId) &&
                Objects.equals(stopHeadsign, stopTime.stopHeadsign) &&
                pickupType == stopTime.pickupType &&
                dropOffType == stopTime.dropOffType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, arrivalTime, departureTime, stopId, stopSequence, stopHeadsign, pickupType, dropOffType, shapeDistTraveled, isTimeExact);
    }

    @Override
    public String toString() {
        return "StopTime{" +
                "tripId='" + tripId + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                ", stopId='" + stopId + '\'' +
                ", stopSequence=" + stopSequence +
                ", stopHeadsign='" + stopHeadsign + '\'' +
                ", pickupType=" + pickupType +
                ", dropOffType=" + dropOffType +
                ", shapeDistTraveled=" + shapeDistTraveled +
                ", isTimeExact=" + isTimeExact +
                '}';
    }
}

package net.eightytwenty.rtd.gtfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * A Trip represents a journey taken by a vehicle through Stops. Trips are time-specific — they are defined as a
 * sequence of StopTimes, so a single Trip represents one journey along a transit line or route. In addition to
 * StopTimes, Trips use Calendars to define the days when a Trip is available to passengers.
 */
public class Trip {
    public static final String HEADER = "block_id,route_id,direction_id,trip_headsign,shape_id,service_id,trip_id";

    private String blockId;
    private String routeId;
    private boolean isInbound;
    private String tripHeadsign;
    private String shapeId;
    private String serviceId;
    private String tripId;

    public Trip(String blockId, String routeId, boolean isInbound, String tripHeadsign, String shapeId, String serviceId, String tripId) {
        this.blockId = blockId;
        this.routeId = routeId;
        this.isInbound = isInbound;
        this.tripHeadsign = tripHeadsign;
        this.shapeId = shapeId;
        this.serviceId = serviceId;
        this.tripId = tripId;
    }

    public static Trip[] parse(InputStream inputStream) throws IOException {
        return GtfsHelper.parseHelper(inputStream,
                Trip.class,
                header -> header.startsWith(HEADER),
                fields -> new Trip(
                        fields[0],
                        fields[1],
                        Integer.parseInt(fields[2]) == 1,
                        fields[3],
                        fields[4],
                        fields[5],
                        fields[6]));
    }

    public String getBlockId() {
        return blockId;
    }

    public String getRouteId() {
        return routeId;
    }

    public boolean getIsInbound() {
        return isInbound;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public String getShapeId() {
        return shapeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getTripId() {
        return tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return isInbound == trip.isInbound &&
                Objects.equals(blockId, trip.blockId) &&
                Objects.equals(routeId, trip.routeId) &&
                Objects.equals(tripHeadsign, trip.tripHeadsign) &&
                Objects.equals(shapeId, trip.shapeId) &&
                Objects.equals(serviceId, trip.serviceId) &&
                Objects.equals(tripId, trip.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, routeId, isInbound, tripHeadsign, shapeId, serviceId, tripId);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "blockId='" + blockId + '\'' +
                ", routeId='" + routeId + '\'' +
                ", isInbound=" + isInbound +
                ", tripHeadsign='" + tripHeadsign + '\'' +
                ", shapeId='" + shapeId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", tripId='" + tripId + '\'' +
                '}';
    }
}

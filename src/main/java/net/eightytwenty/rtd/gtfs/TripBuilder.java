package net.eightytwenty.rtd.gtfs;

public class TripBuilder {

    private String blockId;
    private String routeId;
    private boolean isInbound;
    private String tripHeadsign;
    private String shapeId;
    private String serviceId;
    private String tripId;

    private TripBuilder() {
    }

    public static TripBuilder tripBuilder() { return new TripBuilder();}

    public TripBuilder blockId(String blockId) {
        this.blockId = blockId;
        return this;
    }

    public TripBuilder routeId(String routeId) {
        this.routeId = routeId;
        return this;
    }

    public TripBuilder inbound(boolean inbound) {
        isInbound = inbound;
        return this;
    }

    public TripBuilder tripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
        return this;
    }

    public TripBuilder shapeId(String shapeId) {
        this.shapeId = shapeId;
        return this;
    }

    public TripBuilder serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public TripBuilder tripId(String tripId) {
        this.tripId = tripId;
        return this;
    }

    public Trip build() {
        return new Trip(blockId, routeId, isInbound, tripHeadsign, shapeId, serviceId, tripId);
    }
}

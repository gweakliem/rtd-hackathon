package net.eightytwenty.rtd.service;

import net.eightytwenty.rtd.gtfs.Route;
import net.eightytwenty.rtd.gtfs.Stop;
import net.eightytwenty.rtd.gtfs.StopTime;
import net.eightytwenty.rtd.gtfs.Trip;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by gordon on 10/1/16.
 */
@Service
public class RoutingService {
    public Stream<Stop> findNearestStops(final Stop[] stops, double latitude, double longitude) {
        return Arrays.stream(stops)
                .sorted((stop1, stop2) -> {
                            double d1 = geographicDistance(latitude,
                                    longitude,
                                    stop1.getStopLat(),
                                    stop1.getStopLon());
                            double d2 = geographicDistance(latitude,
                                    longitude,
                                    stop2.getStopLat(),
                                    stop2.getStopLon());
                            return Double.compare(d1, d2);
                        }
                );
    }


    public Optional<Stop> findNearestStop(final Stop[] stops, double latitude, double longitude) {
        return Arrays.stream(stops).min((stop1, stop2) -> {
            double d1 = geographicDistance(latitude,
                    longitude,
                    stop1.getStopLat(),
                    stop1.getStopLon());
            double d2 = geographicDistance(latitude,
                    longitude,
                    stop2.getStopLat(),
                    stop2.getStopLon());
            return Double.compare(d1, d2);
        });
    }

    public List<Route> findRoutingForTrip(final Stop[] stops,
                                          final StopTime[] stopTimes,
                                          final Trip[] trips,
                                          double startLat, double startLong,
                                          double destLat, double destLong) {
        Stream<Stop> destinationStops = findNearestStops(stops, destLat, destLong);

        Stream<Stop> startStops = findNearestStops(stops, startLat, startLong);


        // pick top 3 (?) start / destination
        // traverse stop -> stopTime -> trip (-> route?) and find the matching trip (route?) for start & dest.
        // i.e. we might look at the (n,m) routes that pass through both places and find that there are
        // x routes that have stops in both. Basically find the intersection of routes that touch both stops.
        return null;
    }

    /**
     * Spherical law of cosines implementation for geographic distance.
     * Reference given from http://www.movable-type.co.uk/scripts/latlong.html
     * @param lat1 latitude of first geographic point
     * @param lon1 longitude of first geographic point
     * @param lat2 latitude of second geographic point
     * @param lon2 longitude of second geographic point
     * @return distance between the two points, in meters.
     */
    static double geographicDistance(double lat1, double lon1,
                                     double lat2, double lon2) {
        double φ1 = Math.toRadians(lat1), φ2 = Math.toRadians(lat2);
        double Δλ = Math.toRadians(lon2-lon1);
        final double RADIUS_OF_EARTH = 6371;
        double distance = Math.acos(Math.sin(φ1) * Math.sin(φ2) + Math.cos(φ1) * Math.cos(φ2) * Math.cos(Δλ)) * RADIUS_OF_EARTH;
        return Math.abs(distance);
    }

}

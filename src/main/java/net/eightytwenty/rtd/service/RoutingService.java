package net.eightytwenty.rtd.service;

import net.eightytwenty.rtd.gtfs.Route;
import net.eightytwenty.rtd.gtfs.Stop;
import net.eightytwenty.rtd.gtfs.Trip;
import net.eightytwenty.rtd.model.StopTimeModel;
import net.eightytwenty.rtd.model.TripModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class RoutingService {
    private Stop[] stops;
    private static Map<Trip, TripModel> tripModelMap;
    private static Map<Stop, List<StopTimeModel>> stopTimesByStop;

    @Autowired
    public RoutingService(Stop[] stops, Map<Trip, TripModel> tripModelMap, Map<Stop, List<StopTimeModel>> stopTimesByStop) {
        this.stops = stops;
        this.tripModelMap = tripModelMap;
        this.stopTimesByStop = stopTimesByStop;
    }

    public Stream<Stop> findNearestStops(double latitude, double longitude, double maxDistance) {
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


    public Optional<Stop> findNearestStop(double latitude, double longitude) {
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

    /**
     * Notes
     * http://stackoverflow.com/questions/483488/strategy-to-find-your-best-route-via-public-transportation-only
     * http://pgrouting.org/ - for postgres, in case I want to go that route... haha
     * https://flowingdata.com/2010/03/09/looking-inside-a-bus-routing-algorithm/
     * @param startLat
     * @param startLong
     * @param destLat
     * @param destLong
     * @return
     */
    public List<Route> findRoutingForTrip(double startLat, double startLong,
                                          double destLat, double destLong) {
        List<Stop> destinationStops = findNearestStops(destLat, destLong, 400)
                .limit(20)
                .collect(toList());

        List<Stop> startStops = findNearestStops(startLat, startLong, 400)
                .limit(20)
                .collect(toList());

        // pick top 3 (?) start / destination
        // traverse stop -> stopTime -> trip (-> route?) and find the matching trip (route?) for start & dest.

        List<Route> routesToDest = destinationStops.stream()
                .map(stop -> stopTimesByStop.get(stop)
                        .stream()
                        .map(stm -> stm.getTrip())
                        .collect(toList())
                )
                .flatMap(Collection::stream)
                .map(t -> tripModelMap.get(t).getRoute())
                .collect(toList());

        List<Route> routesFromStart = startStops.stream()
                .map(stop -> stopTimesByStop.get(stop)
                        .stream()
                        .map(stm -> stm.getTrip())
                        .collect(toList())
                )
                .flatMap(Collection::stream)
                .map(t -> tripModelMap.get(t).getRoute())
                .collect(toList());

        return routesToDest.stream()
                .filter(routesFromStart::contains)
                .distinct()
                .collect(toList());
        // i.e. we might look at the (n,m) routes that pass through both places and find that there are
        // x routes that have stops in both. Basically find the intersection of routes that touch both stops.
    }

    /**
     * Spherical law of cosines implementation for geographic distance.
     * Reference given from http://www.movable-type.co.uk/scripts/latlong.html
     * This method used because it's better behaved for small distances, Haversine
     * does not perform well at small tolerances.
     *
     * @param lat1 latitude of first geographic point
     * @param lon1 longitude of first geographic point
     * @param lat2 latitude of second geographic point
     * @param lon2 longitude of second geographic point
     * @return distance between the two points, in meters.
     */
    static double geographicDistance(double lat1, double lon1,
                                     double lat2, double lon2) {
        double φ1 = Math.toRadians(lat1), φ2 = Math.toRadians(lat2);
        double Δλ = Math.toRadians(lon2 - lon1);
        final double RADIUS_OF_EARTH = 6371;
        double distance = Math.acos(Math.sin(φ1) * Math.sin(φ2)
                + Math.cos(φ1) * Math.cos(φ2) * Math.cos(Δλ)) * RADIUS_OF_EARTH;
        return Math.abs(distance);
    }

}

package net.eightytwenty.rtd.service;

import net.eightytwenty.rtd.gtfs.Route;
import net.eightytwenty.rtd.gtfs.Stop;
import net.eightytwenty.rtd.gtfs.StopTime;
import net.eightytwenty.rtd.gtfs.Trip;
import net.eightytwenty.rtd.model.RouteModel;
import net.eightytwenty.rtd.model.StopTimeModel;
import net.eightytwenty.rtd.model.TripModel;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static net.eightytwenty.rtd.gtfs.StopBuilder.stopBuilder;
import static net.eightytwenty.rtd.util.ResourceUtil.getRtdResource;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class RoutingServiceTest {

    private Stop[] stops;

    @Before
    public void setup() throws IOException {
        stops = Stop.parse(getRtdResource("gtfs/stops.txt"), TimeZone.getDefault());
    }

    @Test
    public void findNearestStop_TrivialCase() throws Exception {
        RoutingService service = new RoutingService(stops, null, null);

        stops = new Stop[]{
                stopBuilder().stopLat(39.750931).stopLon(-104.9236592).stopName("23rd Ave & Holly St").build()
        };

        Optional<Stop> nearestStop = service.findNearestStop(39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Holly St"));
    }

    @Test
    public void findNearestStop_SimpleCase() throws Exception {

        stops = new Stop[]{
                stopBuilder().stopLat(39.751003).stopLon(-104.925255).stopName("23rd Ave & Glencoe St").build(),
                stopBuilder().stopLat(39.750662).stopLon(-104.959478).stopName("23rd Ave & York St").build()
        };
        RoutingService service = new RoutingService(stops, null, null);

        Optional<Stop> nearestStop = service.findNearestStop(39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Glencoe St"));
    }

    @Test
    public void findNearestStop_OneStreet() throws Exception {
        RoutingService service = new RoutingService(stops, null, null);

        stops = new Stop[]{
                stopBuilder().stopLat(39.750989).stopLon(-104.932565).stopName("23rd Ave & Dexter St").build(),
                stopBuilder().stopLat(39.751103).stopLon(-104.933099).stopName("23rd Ave & Dexter St").build(),
                stopBuilder().stopLat(39.750995).stopLon(-104.929011).stopName("23rd Ave & Elm St").build(),
                stopBuilder().stopLat(39.75111).stopLon(-104.929883).stopName("23rd Ave & Elm St").build(),
                stopBuilder().stopLat(39.75111).stopLon(-104.936454).stopName("23rd Ave & Birch St").build(),
                stopBuilder().stopLat(39.751124).stopLon(-104.926096).stopName("23rd Ave & Glencoe St").build(),
                stopBuilder().stopLat(39.751013).stopLon(-104.918223).stopName("23rd Ave & Jasmine St").build(),
                stopBuilder().stopLat(39.751132).stopLon(-104.919082).stopName("23rd Ave & Jasmine St").build(),
                stopBuilder().stopLat(39.751137).stopLon(-104.915441).stopName("23rd Ave & Leyden St").build(),
                stopBuilder().stopLat(39.75102).stopLon(-104.914753).stopName("23rd Ave & Leyden St").build(),
                stopBuilder().stopLat(39.750985).stopLon(-104.912538).stopName("23rd Ave & Monaco Pkwy").build(),
                stopBuilder().stopLat(39.751055).stopLon(-104.908029).stopName("23rd Ave & Oneida St").build(),
                stopBuilder().stopLat(39.751109).stopLon(-104.912696).stopName("23rd Ave & Monaco Pkwy").build(),
                stopBuilder().stopLat(39.750962).stopLon(-104.908326).stopName("23rd Ave & Oneida St").build(),
                stopBuilder().stopLat(39.751058).stopLon(-104.906119).stopName("23rd Ave & Pontiac St").build(),
                stopBuilder().stopLat(40.19947).stopLon(-105.106607).stopName("23rd Ave & Pratt St").build(),
                stopBuilder().stopLat(40.199581).stopLon(-105.107508).stopName("23rd Ave & Pratt St").build(),
                stopBuilder().stopLat(39.751062).stopLon(-104.903994).stopName("23rd Ave & Quebec St").build(),
                stopBuilder().stopLat(39.75096).stopLon(-104.899469).stopName("23rd Ave & Syracuse St").build(),
                stopBuilder().stopLat(39.750957).stopLon(-104.905505).stopName("23rd Ave & Pontiac St").build(),
                stopBuilder().stopLat(40.199465).stopLon(-105.110065).stopName("23rd Ave & Gay St").build(),
                stopBuilder().stopLat(40.199468).stopLon(-105.104314).stopName("23rd Ave & Main St").build(),
                stopBuilder().stopLat(39.750974).stopLon(-104.93589).stopName("23rd Ave & Birch St").build(),
                stopBuilder().stopLat(39.751095).stopLon(-104.939679).stopName("23rd Ave & Albion St").build(),
                stopBuilder().stopLat(39.750961).stopLon(-104.903772).stopName("23rd Ave & Quebec St").build(),
                stopBuilder().stopLat(39.751089).stopLon(-104.8993).stopName("23rd Ave & Syracuse St").build(),
                stopBuilder().stopLat(39.751003).stopLon(-104.925255).stopName("23rd Ave & Glencoe St").build(),
                stopBuilder().stopLat(39.751001).stopLon(-104.921986).stopName("23rd Ave & Holly St").build(),
                stopBuilder().stopLat(39.751132).stopLon(-104.922766).stopName("23rd Ave & Holly St").build(),
                stopBuilder().stopLat(39.750662).stopLon(-104.959478).stopName("23rd Ave & York St").build(),
                stopBuilder().stopLat(39.750755).stopLon(-104.960201).stopName("23rd Ave & York St").build(),
                stopBuilder().stopLat(39.75097).stopLon(-104.941852).stopName("23rd Ave & Colorado Blvd").build(),
                stopBuilder().stopLat(39.751083).stopLon(-104.90114).stopName("23rd Ave & Roslyn St").build(),
                stopBuilder().stopLat(40.199602).stopLon(-105.103132).stopName("23rd Ave & Main St").build()
        };

        Optional<Stop> nearestStop = service.findNearestStop(39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Holly St"));
    }

    @Test
    public void findNearestStop_UnionStation() {
        RoutingService service = new RoutingService(stops, null, null);

        Optional<Stop> nearestStop = service.findNearestStop(39.752825, -105);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("Union Station"));
    }

    @Test
    public void findNearestStops_ConformsToDistanceLimits() {
        RoutingService service = new RoutingService(stops, null, null);

        Stream<Stop> nearest = service.findNearestStops(39.752825, -105, 400);

        // find out if all the stops are within 400 meters
        nearest.forEach(s -> assertThat(RoutingService.geographicDistance(39.752825, -105,
                s.getStopLat(), s.getStopLon()),
                lessThanOrEqualTo(400.0)));
        // TODO? assert that there are no other stops in the system < 400 meters?
    }

    @Test
    public void findNearestStops_ReturnsStopsInIncreasingDistance() {
        RoutingService service = new RoutingService(stops, null, null);

        stops = new Stop[]{
                stopBuilder().stopLat(39.750989).stopLon(-104.932565).stopName("23rd Ave & Dexter St").build(),
                stopBuilder().stopLat(39.751103).stopLon(-104.933099).stopName("23rd Ave & Dexter St").build(),
                stopBuilder().stopLat(39.750995).stopLon(-104.929011).stopName("23rd Ave & Elm St").build(),
                stopBuilder().stopLat(39.75111).stopLon(-104.929883).stopName("23rd Ave & Elm St").build(),
                stopBuilder().stopLat(39.75111).stopLon(-104.936454).stopName("23rd Ave & Birch St").build(),
                stopBuilder().stopLat(39.751124).stopLon(-104.926096).stopName("23rd Ave & Glencoe St").build(),
                stopBuilder().stopLat(39.751013).stopLon(-104.918223).stopName("23rd Ave & Jasmine St").build(),
                stopBuilder().stopLat(39.751132).stopLon(-104.919082).stopName("23rd Ave & Jasmine St").build(),
                stopBuilder().stopLat(39.751137).stopLon(-104.915441).stopName("23rd Ave & Leyden St").build(),
                stopBuilder().stopLat(39.75102).stopLon(-104.914753).stopName("23rd Ave & Leyden St").build(),
                stopBuilder().stopLat(39.750985).stopLon(-104.912538).stopName("23rd Ave & Monaco Pkwy").build(),
                stopBuilder().stopLat(39.751055).stopLon(-104.908029).stopName("23rd Ave & Oneida St").build(),
                stopBuilder().stopLat(39.751109).stopLon(-104.912696).stopName("23rd Ave & Monaco Pkwy").build(),
                stopBuilder().stopLat(39.750962).stopLon(-104.908326).stopName("23rd Ave & Oneida St").build(),
                stopBuilder().stopLat(39.751058).stopLon(-104.906119).stopName("23rd Ave & Pontiac St").build(),
                stopBuilder().stopLat(40.19947).stopLon(-105.106607).stopName("23rd Ave & Pratt St").build(),
                stopBuilder().stopLat(40.199581).stopLon(-105.107508).stopName("23rd Ave & Pratt St").build(),
                stopBuilder().stopLat(39.751062).stopLon(-104.903994).stopName("23rd Ave & Quebec St").build(),
                stopBuilder().stopLat(39.75096).stopLon(-104.899469).stopName("23rd Ave & Syracuse St").build(),
                stopBuilder().stopLat(39.750957).stopLon(-104.905505).stopName("23rd Ave & Pontiac St").build(),
                stopBuilder().stopLat(40.199465).stopLon(-105.110065).stopName("23rd Ave & Gay St").build(),
                stopBuilder().stopLat(40.199468).stopLon(-105.104314).stopName("23rd Ave & Main St").build(),
                stopBuilder().stopLat(39.750974).stopLon(-104.93589).stopName("23rd Ave & Birch St").build(),
                stopBuilder().stopLat(39.751095).stopLon(-104.939679).stopName("23rd Ave & Albion St").build(),
                stopBuilder().stopLat(39.750961).stopLon(-104.903772).stopName("23rd Ave & Quebec St").build(),
                stopBuilder().stopLat(39.751089).stopLon(-104.8993).stopName("23rd Ave & Syracuse St").build(),
                stopBuilder().stopLat(39.751003).stopLon(-104.925255).stopName("23rd Ave & Glencoe St").build(),
                stopBuilder().stopLat(39.751001).stopLon(-104.921986).stopName("23rd Ave & Holly St").build(),
                stopBuilder().stopLat(39.751132).stopLon(-104.922766).stopName("23rd Ave & Holly St").build(),
                stopBuilder().stopLat(39.750662).stopLon(-104.959478).stopName("23rd Ave & York St").build(),
                stopBuilder().stopLat(39.750755).stopLon(-104.960201).stopName("23rd Ave & York St").build(),
                stopBuilder().stopLat(39.75097).stopLon(-104.941852).stopName("23rd Ave & Colorado Blvd").build(),
                stopBuilder().stopLat(39.751083).stopLon(-104.90114).stopName("23rd Ave & Roslyn St").build(),
                stopBuilder().stopLat(40.199602).stopLon(-105.103132).stopName("23rd Ave & Main St").build()
        };

        List<Stop> nearestStops = service.findNearestStops(39.751083, -104.90114, 400).collect(toList());
        assertThat(nearestStops.get(0).getStopName(), equalTo("23rd Ave & Roslyn St"));
    }

    @Test
    public void findNearestStop_LargeDataset() throws Exception {
        RoutingService service = new RoutingService(stops, null, null);

        Optional<Stop> nearestStop = service.findNearestStop(39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Holly St"));
    }

    @Test
    public void geographicDistance_ReturnsCorrectValueWithinTolerance() {
        double v1 = RoutingService.geographicDistance(39.751089, -104.8993, 39.750931, -104.9236592);

        // we're asserting that our distance calculation is good to about 5 meters (based on distance
        // calculated for these points at the http://www.movable-type.co.uk/scripts/latlong.html site).
        assertEquals(2.083, v1, 0.005);
    }

    @Test
    public void findRoutingForTrip_ReturnsExpectedRoutes() throws IOException {
        Map<String, RouteModel> routeLookup;
        Map<String, Stop> stopLookup;
        Map<String, TripModel> tripLookup;
        Route[] routes;
        Stop[] stops;
        Trip[] trips;
        StopTime[] stopTimes;

        routes = Route.parse(getRtdResource("gtfs/routes.txt"));
        // route - join to Agency on the agency_id column
        routeLookup = Arrays.stream(routes)
                .map(route -> new RouteModel(route, null))
                .collect(toMap(r -> r.getRoute().getRouteId(), Function.identity()));

        stops = Stop.parse(getRtdResource("gtfs/stops.txt"), TimeZone.getDefault());
        stopLookup = Arrays.stream(stops)
                .collect(Collectors.toMap(Stop::getStopId, Function.identity()));

        trips = Trip.parse(getRtdResource("gtfs/trips.txt"));
        tripLookup = Arrays.stream(trips)
                .map(trip -> new TripModel(trip,
                        routeLookup.get(trip.getRouteId()).getRoute(),
                        null)
                )
                .collect(toMap(tm -> tm.getTrip().getTripId(),
                        Function.identity()));

        stopTimes = StopTime.parse(getRtdResource("gtfs/stop_times.txt"));
        // join to stop on  stop_id column
        // join to trip on trip_id column
        Map<Stop, List<StopTimeModel>> stopListMap = Arrays.stream(stopTimes)
                .map(st -> new StopTimeModel(st,
                        stopLookup.get(st.getStopId()),
                        tripLookup.get(st.getTripId()).getTrip()
                ))
                .collect(groupingBy(StopTimeModel::getStop));

        Map<Trip, TripModel> tripModelMap = Arrays.stream(trips)
                .map(trip -> new TripModel(trip,
                        routeLookup.get(trip.getRouteId()).getRoute(),
                        null)
                )
                .collect(toMap(tm -> tm.getTrip(),
                        Function.identity()));

        RoutingService service = new RoutingService(stops, tripModelMap, stopListMap);

        List<Route> routingForTrip = service.findRoutingForTrip(39.750931, -104.9236592, 39.752825, -105);


        assertThat(routingForTrip.stream().map(Route::getRouteId).collect(toList()).toArray(),
                arrayContainingInAnyOrder("20", "28", "28B"));
    }

    @Test
    public void findRouting_ReturnsExpectedRoute() {
        //RoutingService service = new RoutingService(stops, tripModelMap, stopListMap);

        //List<Route> routes = service.findRoutes(39.750931, -104.9236592, 39.752825, -105);


    }
}
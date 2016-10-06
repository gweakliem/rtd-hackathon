package net.eightytwenty.rtd.service;

import net.eightytwenty.rtd.gtfs.Stop;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static net.eightytwenty.rtd.gtfs.StopBuilder.stopBuilder;
import static net.eightytwenty.rtd.util.ResourceUtil.getRtdResource;
import static org.hamcrest.Matchers.startsWith;
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
        RoutingService service = new RoutingService();

        stops = new Stop[]{
                stopBuilder().stopLat(39.750931).stopLon(-104.9236592).stopName("23rd Ave & Holly St").build()
        };

        Optional<Stop> nearestStop = service.findNearestStop(stops, 39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Holly St"));
    }

    @Test
    public void findNearestStop_SimpleCase() throws Exception {
        RoutingService service = new RoutingService();

        stops = new Stop[]{
                stopBuilder().stopLat(39.751003).stopLon(-104.925255).stopName("23rd Ave & Glencoe St").build(),
                stopBuilder().stopLat(39.750662).stopLon(-104.959478).stopName("23rd Ave & York St").build()
        };

        Optional<Stop> nearestStop = service.findNearestStop(stops, 39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Glencoe St"));
    }

    @Test
    public void findNearestStop_OneStreet() throws Exception {
        RoutingService service = new RoutingService();

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

        Optional<Stop> nearestStop = service.findNearestStop(stops, 39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Holly St"));
    }

    @Test
    public void findNearestStop_UnionStation() {
        RoutingService service = new RoutingService();

        Optional<Stop> nearestStop = service.findNearestStop(stops, 39.752825, -105);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("Union Station"));
    }

    @Test
    public void findNearestStops_UnionStation_Substations() {
        RoutingService service = new RoutingService();

        Stream<Stop> nearest = service.findNearestStops(stops, 39.752825, -105);

        nearest.limit(5)
                .peek(s -> assertThat(s.getStopName(),startsWith("Union Station")))
                .collect(toList());
    }

    @Test
    public void findNearestStop_LargeDataset() throws Exception {
        RoutingService service = new RoutingService();

        Optional<Stop> nearestStop = service.findNearestStop(stops, 39.750931, -104.9236592);
        assertTrue(nearestStop.isPresent());
        Stop stop = nearestStop.get();
        assertThat(stop.getStopName(), equalTo("23rd Ave & Holly St"));
    }

    @Test
    public void geographicDistance() {
        double v1 = RoutingService.geographicDistance(39.751089, -104.8993, 39.750931, -104.9236592);

        // we're asserting that our distance calculation is good to about 5 meters (based on distance
        // calculated for these points at the http://www.movable-type.co.uk/scripts/latlong.html site).
        assertEquals(2.083, v1, 0.005);
    }
}
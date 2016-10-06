package net.eightytwenty.rtd.gtfs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by gordon on 10/1/16.
 */
public class RouteTest {

    @Test
    public void parse() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("gtfs/routes.txt").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        Route[] routes = Route.parse(fileInputStream);

        Route route = routes[8];
        assertThat(route.getRouteLongName(), equalTo("Yale / Buckley"));
        assertThat(route.getRouteType(), equalTo(Route.RouteType.BUS));
        assertThat(route.getRouteTextColor(), equalTo("FFFFFF"));
        assertThat(route.getAgencyId(), equalTo("RTD"));
        assertThat(route.getRouteId(), equalTo("130"));
        assertThat(route.getRouteColor(), equalTo("0076CE"));
        assertThat(route.getRouteDesc(),equalTo("This Route Travels Northbound & Southbound"));
        assertThat(route.getRouteUrl(), equalTo("http://www.rtd-denver.com/Schedules.shtml"));
        assertThat(route.getRouteShortName(),equalTo("130"));
    }
}
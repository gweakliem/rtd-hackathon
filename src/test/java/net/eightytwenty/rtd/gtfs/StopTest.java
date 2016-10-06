package net.eightytwenty.rtd.gtfs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.TimeZone;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by gordon on 10/1/16.
 */
public class StopTest {

    @Test
    public void parse() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("gtfs/stops.txt").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        Stop[] stops = Stop.parse(fileInputStream, TimeZone.getDefault());

        Stop stop = stops[8];

        assertThat(stop.getStopLat(), equalTo(39.725646));
        assertTrue(stop.isWheelchairBoarding());
        assertThat(stop.getStopCode(), equalTo("11548"));
        assertThat(stop.getStopLon(),equalTo(-104.870947));
        assertThat(stop.getStopTimezone(), equalTo(TimeZone.getDefault()));
        assertThat(stop.getStopUrl(), equalTo(""));
        assertThat(stop.getParentStation(), equalTo(""));
        assertThat(stop.getStopDesc(), equalTo("Vehicles Travelling West"));
        assertThat(stop.getStopName(), equalTo("6th Ave & Fulton St"));
        assertFalse(stop.isStation());
        assertThat(stop.getStopId(), equalTo("11548"));
        assertNull(stop.getZoneId());
    }
}
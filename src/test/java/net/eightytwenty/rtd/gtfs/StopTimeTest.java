package net.eightytwenty.rtd.gtfs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by gordon on 9/30/16.
 */
public class StopTimeTest {

    @Test
    public void parse() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("gtfs/stop_times.txt").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        StopTime[] stopTimes = StopTime.parse(fileInputStream);
        StopTime stopTime = stopTimes[5];
        assertThat(stopTime.getTripId(), equalTo("110220281"));
        assertThat(stopTime.getArrivalTime(), equalTo(Duration.parse("PT10H30M05S")));
        assertThat(stopTime.getDepartureTime(), equalTo(Duration.parse("PT10H30M05S")));
        assertThat(stopTime.getStopId(), equalTo("25676"));
        assertThat(stopTime.getStopSequence(), equalTo(6));
    }
}
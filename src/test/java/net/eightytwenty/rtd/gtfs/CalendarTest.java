package net.eightytwenty.rtd.gtfs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by gordon on 9/30/16.
 */
public class CalendarTest {

    @Test
    public void parse() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("gtfs/calendar.txt").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        Calendar[] calendars = Calendar.parse(fileInputStream);

        Calendar calendar = calendars[8];
        assertThat(calendar.getServiceId(),equalTo("SA_merged_110304395"));
        assertThat(calendar.getStartDate(), equalTo(LocalDate.of(2016,10,23)));
        assertThat(calendar.getEndDate(), equalTo(LocalDate.of(2016,12,17)));
        assertFalse(calendar.isRunsMonday());
        assertFalse(calendar.isRunsTuesday());
        assertFalse(calendar.isRunsWednesday());
        assertFalse(calendar.isRunsThursday());
        assertFalse(calendar.isRunsFriday());
        assertTrue(calendar.isRunsSaturday());
        assertFalse(calendar.isRunsSunday());
    }
}
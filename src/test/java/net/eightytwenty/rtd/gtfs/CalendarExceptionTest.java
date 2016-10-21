package net.eightytwenty.rtd.gtfs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class CalendarExceptionTest {

    @Test
    public void parse() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("gtfs/calendar_dates.txt").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);

        CalendarException[] calendarExceptions = CalendarException.parse(fileInputStream);

        assertThat(calendarExceptions[0].getServiceId(), equalTo("WK_merged_110304389"));
        assertThat(calendarExceptions[0].getDate(), equalTo(LocalDate.of(2016,9,5)));
        assertThat(calendarExceptions[0].getExceptionType(), equalTo(CalendarException.ExceptionType.SERVICE_REMOVED));

    }
}
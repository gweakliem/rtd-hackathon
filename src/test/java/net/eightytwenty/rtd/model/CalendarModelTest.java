package net.eightytwenty.rtd.model;

import net.eightytwenty.rtd.gtfs.Calendar;
import net.eightytwenty.rtd.gtfs.CalendarException;
import net.eightytwenty.rtd.gtfs.CalendarException.ExceptionType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static net.eightytwenty.rtd.gtfs.CalendarException.ExceptionType.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CalendarModelTest {
    private CalendarModel model;

    @Before
    public void setup() throws IOException {
        Map<String, Calendar> calendars = new HashMap<>();
        calendars.put("NotFriday", new Calendar("NotFriday", LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31),
                true, true, true, true, false, true, true));

        Map<String, CalendarException> calendarExceptions = Collections.emptyMap();


        model = new CalendarModel(calendars, calendarExceptions);
    }

    @Test
    public void serviceAvailable_HandlesServiceWithNoException() {
        assertFalse(model.serviceAvailable("NotFriday", LocalDate.of(2016, 10, 21)));
        assertTrue(model.serviceAvailable("NotFriday", LocalDate.of(2016, 10, 22)));
    }

    @Test
    public void serviceAvailable_IgnoresIrrelevantExceptions() {
        Map<String, Calendar> calendars = new HashMap<>();
        calendars.put("NotFriday", new Calendar("NotFriday", LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31),
                true, true, true, true, false, true, true));

        Map<String, CalendarException> calendarExceptions = new HashMap<>();
        calendarExceptions.put("NotFriday", new CalendarException("NotFriday", LocalDate.of(2016, 1, 1), SERVICE_REMOVED));

        model = new CalendarModel(calendars, calendarExceptions);

        assertTrue(model.serviceAvailable("NotFriday", LocalDate.of(2016, 10, 20)));

    }

    @Test
    public void serviceAvailable_ProcessesRemovalExceptions() throws Exception {
        Map<String, Calendar> calendars = new HashMap<>();
        calendars.put("NotFriday", new Calendar("NotFriday", LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31),
                true, true, true, true, false, true, true));

        Map<String, CalendarException> calendarExceptions = new HashMap<>();
        calendarExceptions.put("NotFriday", new CalendarException("NotFriday", LocalDate.of(2016, 9, 5), SERVICE_REMOVED));

        model = new CalendarModel(calendars, calendarExceptions);
        assertFalse(model.serviceAvailable("NotFriday", LocalDate.of(2016, 9, 5)));
    }

    @Test
    public void serviceAvailable_ProcessesAdditionExceptions() throws Exception {
        Map<String, Calendar> calendars = new HashMap<>();
        calendars.put("NotFriday", new Calendar("NotFriday", LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31),
                true, true, true, true, false, true, true));

        Map<String, CalendarException> calendarExceptions = new HashMap<>();
        calendarExceptions.put("NotFriday", new CalendarException("NotFriday", LocalDate.of(2016, 10, 21), SERVICE_ADDED));
        model = new CalendarModel(calendars, calendarExceptions);

        assertTrue(model.serviceAvailable("NotFriday", LocalDate.of(2016, 10, 21)));
    }

}
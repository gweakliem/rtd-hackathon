package net.eightytwenty.rtd.model;

import net.eightytwenty.rtd.gtfs.Calendar;
import net.eightytwenty.rtd.gtfs.CalendarException;

import java.time.LocalDate;
import java.util.Map;

import static net.eightytwenty.rtd.gtfs.CalendarException.ExceptionType.SERVICE_ADDED;

/**
 * Aggregates the Calendar and CalendarException gtfs types into a unified view
 */
public class CalendarModel {
    private Map<String, Calendar> calendarByServiceId;
    private Map<String, CalendarException> calendarExceptionByServiceId;

    public CalendarModel(Map<String, Calendar> calendars, Map<String, CalendarException> calendarExceptions) {

        this.calendarByServiceId = calendars;
        this.calendarExceptionByServiceId = calendarExceptions;
    }

    public boolean serviceAvailable(String serviceId, LocalDate serviceDate) {
        Calendar calendar = calendarByServiceId.get(serviceId);

        CalendarException ex = calendarExceptionByServiceId.get(serviceId);

        if (ex != null) {
            if (serviceDate.equals(ex.getDate())) {
                return ex.getExceptionType().equals(SERVICE_ADDED);
            }
        }

        switch (serviceDate.getDayOfWeek()) {
            case SUNDAY:
                return calendar.isRunsSunday();
            case MONDAY:
                return calendar.isRunsMonday();
            case TUESDAY:
                return calendar.isRunsTuesday();
            case WEDNESDAY:
                return calendar.isRunsWednesday();
            case THURSDAY:
                return calendar.isRunsThursday();
            case FRIDAY:
                return calendar.isRunsFriday();
            case SATURDAY:
                return calendar.isRunsSaturday();
        }
        return false;
    }
}

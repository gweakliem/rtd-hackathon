package net.eightytwenty.rtd.gtfs;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * GTFS Calendar_dates type
 * https://developers.google.com/transit/gtfs/reference/calendar_dates-file
 */
public class CalendarException {
    public static final String HEADER = "service_id,date,exception_type";
    private String serviceId;
    private LocalDate date;
    private ExceptionType exceptionType;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");


    public enum ExceptionType {
        SERVICE_ADDED(1),
        SERVICE_REMOVED(2);

        private int numericValue;

        ExceptionType(int value) {
            numericValue = value;
        }

        public static final Map<String, ExceptionType> ORDINAL_MAP =
                Collections.unmodifiableMap(new HashMap<String, ExceptionType>() {
                    {
                        put("1", SERVICE_ADDED);
                        put("2", SERVICE_REMOVED);
                    }
                });


        private static ExceptionType parseRouteType(String ordinalValue) {
            String deref = Optional.ofNullable(ordinalValue).orElse("0");
            return ORDINAL_MAP.get(deref);
        }

    }

    public CalendarException(String serviceId,
                             LocalDate date,
                             ExceptionType exceptionType) {
        this.serviceId = serviceId;
        this.date = date;
        this.exceptionType = exceptionType;
    }

    public static CalendarException[] parse(InputStream inputStream) throws IOException {
        return GtfsHelper.parseHelper(
                inputStream,
                CalendarException.class,
                header -> header.startsWith(HEADER),
                fields -> new CalendarException(
                        fields[0],
                        LocalDate.parse(fields[1], DATE_FORMAT),
                        ExceptionType.parseRouteType(fields[2])
                ));
    }

    public String getServiceId() {
        return serviceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarException that = (CalendarException) o;
        return Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(date, that.date) &&
                exceptionType == that.exceptionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, date, exceptionType);
    }

    @Override
    public String toString() {
        return "CalendarException{" +
                "serviceId='" + serviceId + '\'' +
                ", date='" + date + '\'' +
                ", exceptionType=" + exceptionType +
                '}';
    }
}

package net.eightytwenty.rtd.gtfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * GTFS Calendar_dates type
 * https://developers.google.com/transit/gtfs/reference/calendar_dates-file
 */
public class CalendarException {
    public static final String HEADER = "service_id,date,exception_type";
    private String serviceId;
    private String date;
    private ExceptionType exceptionType;

    enum ExceptionType {
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
                             String date,
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
                        fields[1],
                        ExceptionType.parseRouteType(fields[2])
                ));
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getDate() {
        return date;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}

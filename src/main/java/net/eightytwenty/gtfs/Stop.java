package net.eightytwenty.gtfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

import static net.eightytwenty.gtfs.GtfsHelper.tryGet;

/**
 * Created by gordon on 9/29/16.
 */
public class Stop {
    public static final String HEADER =
            "stop_lat,wheelchair_boarding,stop_code,stop_lon,stop_timezone,stop_url,parent_station,stop_desc,stop_name,location_type,stop_id,zone_id";

    private double stopLat;
    private boolean wheelchairBoarding;
    private String stopCode;
    private double stopLon;
    private TimeZone stopTimezone;
    private String stopUrl;
    private String parentStation;
    private String stopDesc;
    private String stopName;
    private boolean isStation;
    private String stopId;
    private String zoneId;

    private Stop(double stopLat, boolean wheelchairBoarding, String stopCode, double stopLon, TimeZone stopTimezone, String stopUrl, String parentStation, String stopDesc, String stopName, boolean isStation, String stopId, String zoneId) {
        this.stopLat = stopLat;
        this.wheelchairBoarding = wheelchairBoarding;
        this.stopCode = stopCode;
        this.stopLon = stopLon;
        this.stopTimezone = stopTimezone;
        this.stopUrl = stopUrl;
        this.parentStation = parentStation;
        this.stopDesc = stopDesc;
        this.stopName = stopName;
        this.isStation = isStation;
        this.stopId = stopId;
        this.zoneId = zoneId;
    }

    /**
     * Parse the Stop records from the CSV text file supplied by the transit agency.
     * @param input
     * @param defaultTimezone - timezone used for records that don't supply one. Normally this would
     *                        be the timezone specified for the agency in agency.txt
     * @return
     * @throws IOException
     */
    public static Stop[] parse(InputStream input, TimeZone defaultTimezone) throws IOException {
        return GtfsHelper.parseHelper(input,
                Stop.class,
                header -> header.startsWith(HEADER),
                fields -> {
                    String timeZone = tryGet(fields, 4, "");
                    if (timeZone.isEmpty()) {
                        timeZone = defaultTimezone.getID();
                    }
                    return new Stop(
                            Double.parseDouble(fields[0]),
                            Integer.parseInt(fields[1]) == 1,
                            fields[2],
                            Double.parseDouble(fields[3]),
                            TimeZone.getTimeZone(timeZone),
                            fields[5],
                            fields[6],
                            fields[7],
                            fields[8],
                            tryGet(fields, 9, "1").equals("1"),
                            tryGet(fields, 10),
                            tryGet(fields, 11)
                    );
                });
    }

    public double getStopLat() {
        return stopLat;
    }

    public boolean isWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public String getStopCode() {
        return stopCode;
    }

    public double getStopLon() {
        return stopLon;
    }

    public TimeZone getStopTimezone() {
        return stopTimezone;
    }

    public String getStopUrl() {
        return stopUrl;
    }

    public String getParentStation() {
        return parentStation;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public String getStopName() {
        return stopName;
    }

    public boolean isStation() {
        return isStation;
    }

    public String getStopId() {
        return stopId;
    }

    public String getZoneId() {
        return zoneId;
    }
}

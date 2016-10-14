package net.eightytwenty.rtd.gtfs;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Created by gordon on 9/29/16.
 */
public class Calendar {
    public static final String HEADER = "service_id,start_date,end_date,monday,tuesday,wednesday,thursday,friday,saturday,sunday";

    private String serviceId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean runsMonday;
    private boolean runsTuesday;
    private boolean runsWednesday;
    private boolean runsThursday;
    private boolean runsFriday;
    private boolean runsSaturday;
    private boolean runsSunday;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private Calendar(String serviceId, LocalDate startDate, LocalDate endDate, boolean runsMonday, boolean runsTuesday, boolean runsWednesday, boolean runsThursday, boolean runsFriday, boolean runsSaturday, boolean runsSunday) {
        this.serviceId = serviceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runsMonday = runsMonday;
        this.runsTuesday = runsTuesday;
        this.runsWednesday = runsWednesday;
        this.runsThursday = runsThursday;
        this.runsFriday = runsFriday;
        this.runsSaturday = runsSaturday;
        this.runsSunday = runsSunday;
    }

    public static boolean isLegalHeader(String header) {
        return HEADER.startsWith(header);
    }

    public static Calendar[] parse(InputStream input) throws IOException {
        return GtfsHelper.parseHelper(input,
                Calendar.class,
                Calendar::isLegalHeader,
                Calendar::parseObject
        );
    }

    private static Calendar parseObject(String[] fields) {
        return new Calendar(
                fields[0],
                LocalDate.parse(fields[1], DATE_FORMAT),
                LocalDate.parse(fields[2], DATE_FORMAT),
                Integer.parseInt(fields[3]) == 1,
                Integer.parseInt(fields[4]) == 1,
                Integer.parseInt(fields[5]) == 1,
                Integer.parseInt(fields[6]) == 1,
                Integer.parseInt(fields[7]) == 1,
                Integer.parseInt(fields[8]) == 1,
                Integer.parseInt(fields[9]) == 1);
    }

    public String getServiceId() {
        return serviceId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isRunsMonday() {
        return runsMonday;
    }

    public boolean isRunsTuesday() {
        return runsTuesday;
    }

    public boolean isRunsWednesday() {
        return runsWednesday;
    }

    public boolean isRunsThursday() {
        return runsThursday;
    }

    public boolean isRunsFriday() {
        return runsFriday;
    }

    public boolean isRunsSaturday() {
        return runsSaturday;
    }

    public boolean isRunsSunday() {
        return runsSunday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendar calendar = (Calendar) o;
        return runsMonday == calendar.runsMonday &&
                runsTuesday == calendar.runsTuesday &&
                runsWednesday == calendar.runsWednesday &&
                runsThursday == calendar.runsThursday &&
                runsFriday == calendar.runsFriday &&
                runsSaturday == calendar.runsSaturday &&
                runsSunday == calendar.runsSunday &&
                Objects.equals(serviceId, calendar.serviceId) &&
                Objects.equals(startDate, calendar.startDate) &&
                Objects.equals(endDate, calendar.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, startDate, endDate, runsMonday, runsTuesday, runsWednesday, runsThursday, runsFriday, runsSaturday, runsSunday);
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "serviceId='" + serviceId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", runsMonday=" + runsMonday +
                ", runsTuesday=" + runsTuesday +
                ", runsWednesday=" + runsWednesday +
                ", runsThursday=" + runsThursday +
                ", runsFriday=" + runsFriday +
                ", runsSaturday=" + runsSaturday +
                ", runsSunday=" + runsSunday +
                '}';
    }
}
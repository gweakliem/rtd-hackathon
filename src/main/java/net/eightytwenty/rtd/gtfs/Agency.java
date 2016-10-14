package net.eightytwenty.rtd.gtfs;

import java.io.*;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class Agency {
    public static final String HEADER = "agency_url,agency_name,agency_timezone,agency_id,agency_lang";

    private String url;
    private String name;
    private TimeZone timezone;
    private String id;

    private Locale language;

    private Agency(String url, String name, TimeZone timezone, String id, Locale language) {
        this.url = url;
        this.name = name;
        this.timezone = timezone;
        this.id = id;
        this.language = language;
    }

    public static Agency[] parse(InputStream input) throws IOException {
        return GtfsHelper.parseHelper(input,
                Agency.class,
                header -> header.startsWith(HEADER),
                agencyFields -> new Agency(agencyFields[0],
                        agencyFields[1],
                        TimeZone.getTimeZone(agencyFields[2]),
                        agencyFields[3],
                        Locale.forLanguageTag(agencyFields[4])));
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public String getId() {
        return id;
    }

    public Locale getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return Objects.equals(url, agency.url) &&
                Objects.equals(name, agency.name) &&
                Objects.equals(timezone, agency.timezone) &&
                Objects.equals(id, agency.id) &&
                Objects.equals(language, agency.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name, timezone, id, language);
    }

    @Override
    public String toString() {
        return "Agency{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", timezone=" + timezone +
                ", id='" + id + '\'' +
                ", language=" + language +
                '}';
    }
}

package net.eightytwenty.rtd.gtfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static net.eightytwenty.rtd.gtfs.GtfsHelper.tryGet;

public class Route {
    public static final String HEADER = "route_long_name,route_type,route_text_color,agency_id,route_id,route_color,route_desc,route_url,route_short_name";

    public Route(String routeLongName, RouteType routeType, String routeTextColor, String agencyId, String routeId, String routeColor, String routeDesc, String routeUrl, String routeShortName) {
        this.routeLongName = routeLongName;
        this.routeType = routeType;
        this.routeTextColor = routeTextColor;
        this.agencyId = agencyId;
        this.routeId = routeId;
        this.routeColor = routeColor;
        this.routeDesc = routeDesc;
        this.routeUrl = routeUrl;
        this.routeShortName = routeShortName;
    }

    public enum RouteType {
        TRAM(0),
        SUBWAY(1),
        RAIL(2),
        BUS(3),
        FERRY(4),
        CABLE_CAR(5),
        GONDOLA(6),
        FUNICULAR(7);

        private final int numericValue;

        public static final Map<String, RouteType> ORDINAL_MAP =
                Collections.unmodifiableMap(new HashMap<String, RouteType>() {
                    {
                        put("0", TRAM);
                        put("1", SUBWAY);
                        put("2", RAIL);
                        put("3", BUS);
                        put("4", FERRY);
                        put("5", CABLE_CAR);
                        put("6", GONDOLA);
                        put("7", FUNICULAR);
                    }
                });

        RouteType(int intValue) {
            numericValue = intValue;
        }

        private static RouteType parseRouteType(String ordinalValue) {
            String deref = Optional.ofNullable(ordinalValue).orElse("0");
            return ORDINAL_MAP.get(deref);
        }
    }

    public static Route[] parse(InputStream inputStream) throws IOException {
        return GtfsHelper.parseHelper(inputStream,
                Route.class,
                header -> header.startsWith(HEADER),
                fields -> new Route(
                        fields[0],
                        RouteType.parseRouteType(tryGet(fields, 1)),
                        fields[2],
                        fields[3],
                        fields[4],
                        fields[5],
                        fields[6],
                        fields[7],
                        fields[8]
                ));
    }

    private String routeLongName;
    private RouteType routeType;
    private String routeTextColor;
    private String agencyId;
    private String routeId;
    private String routeColor;
    private String routeDesc;
    private String routeUrl;

    public String getRouteShortName() {
        return routeShortName;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    private String routeShortName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(routeLongName, route.routeLongName) &&
                routeType == route.routeType &&
                Objects.equals(routeTextColor, route.routeTextColor) &&
                Objects.equals(agencyId, route.agencyId) &&
                Objects.equals(routeId, route.routeId) &&
                Objects.equals(routeColor, route.routeColor) &&
                Objects.equals(routeDesc, route.routeDesc) &&
                Objects.equals(routeUrl, route.routeUrl) &&
                Objects.equals(routeShortName, route.routeShortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeLongName, routeType, routeTextColor, agencyId, routeId, routeColor, routeDesc, routeUrl, routeShortName);
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeLongName='" + routeLongName + '\'' +
                ", routeType=" + routeType +
                ", routeTextColor='" + routeTextColor + '\'' +
                ", agencyId='" + agencyId + '\'' +
                ", routeId='" + routeId + '\'' +
                ", routeColor='" + routeColor + '\'' +
                ", routeDesc='" + routeDesc + '\'' +
                ", routeUrl='" + routeUrl + '\'' +
                ", routeShortName='" + routeShortName + '\'' +
                '}';
    }
}

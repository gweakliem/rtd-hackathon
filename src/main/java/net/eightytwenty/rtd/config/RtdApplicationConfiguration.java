package net.eightytwenty.rtd.config;

import net.eightytwenty.rtd.gtfs.*;
import net.eightytwenty.rtd.model.RouteModel;
import net.eightytwenty.rtd.model.StopTimeModel;
import net.eightytwenty.rtd.model.TripModel;
import net.eightytwenty.rtd.rtdapi.GtfsDataInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sun.security.acl.PrincipalImpl;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static net.eightytwenty.rtd.util.ResourceUtil.getRtdResource;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;

@Configuration
public class RtdApplicationConfiguration {

    private static Agency rtd;
    private static Map<String, Calendar> calendarLookup;
    private static Map<String, RouteModel> routeLookup;
    private static Map<Trip, TripModel> tripModelMap;
    private static Map<Stop, StopTimeModel> stopTimesByStop;
    private static Agency[] agencies;
    private static Calendar[] calendars;
    private static Route[] routes;
    private static Stop[] stops;
    private static Trip[] trips;
    private static StopTime[] stopTimes;

    public static void buildObjectModels() throws IOException {
        agencies = Agency.parse(getRtdResource("gtfs/agency.txt"));
        rtd = Arrays.stream(agencies).findFirst().orElse(null);

        calendars = Calendar.parse(getRtdResource("gtfs/calendar.txt"));
        calendarLookup = Arrays.stream(calendars)
                .collect(Collectors.toMap(Calendar::getServiceId, Function.identity()));

        routes = Route.parse(getRtdResource("gtfs/routes.txt"));
        // route - join to Agency on the agency_id column
        routeLookup = Arrays.stream(routes)
                .map(route -> new RouteModel(route, rtd))
                .collect(toMap(r -> r.getRoute().getRouteId(), Function.identity()));

        stops = Stop.parse(getRtdResource("gtfs/stops.txt"), rtd.getTimezone());
        Map<String, Stop> stopLookup = Arrays.stream(stops)
                .collect(Collectors.toMap(Stop::getStopId, Function.identity()));

        trips = Trip.parse(getRtdResource("gtfs/trips.txt"));
        tripModelMap = Arrays.stream(trips)
                .map(trip -> new TripModel(trip,
                        routeLookup.get(trip.getRouteId()).getRoute(),
                        calendarLookup.get(trip.getServiceId())
                ))
                .collect(toMap(tm -> tm.getTrip(),
                        Function.identity()));

        stopTimes = StopTime.parse(getRtdResource("gtfs/stop_times.txt"));
        // join to stop on  stop_id column
        // join to trip on trip_id column
        stopTimesByStop = Arrays.stream(stopTimes)
                .map(st -> new StopTimeModel(st,
                        stopLookup.get(st.getStopId()),
                        tripModelMap.get(st.getTripId()).getTrip()
                ))
                .collect(toMap(stm -> stm.getStop(), Function.identity()));

    }

    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(hmc));
        restTemplate.setInterceptors(asList(new GtfsDataInterceptor()));
        return restTemplate;
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    CredentialsProvider credentialsProvider() {
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope("rtd-denver.com", 80),
                new Credentials() {
                    @Override
                    // TODO - this is a deprecated interface, find alternate method
                    public Principal getUserPrincipal() {
                        return new PrincipalImpl("RTDgtfsRT");
                    }

                    @Override
                    public String getPassword() {
                        return "realT!m3Fee";
                    }
                });
        return provider;
    }
}

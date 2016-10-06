package net.eightytwenty.rtd.controller;

import net.eightytwenty.rtd.gtfs.*;
import net.eightytwenty.rtd.model.RouteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static net.eightytwenty.rtd.util.ResourceUtil.getRtdResource;

@Controller
public class HomeController {

    @RequestMapping("/")
    String home(@RequestParam(value = "from", required = false) String from,
                @RequestParam(value = "to", required = false) String to,
                Model model) throws Exception {

        Agency[] agencies = Agency.parse(getRtdResource("gtfs/agency.txt"));
        Agency rtd = Arrays.stream(agencies).findFirst().orElse(null);

        Calendar[] calendars = Calendar.parse(getRtdResource("gtfs/calendar.txt"));

        Route[] routes = Route.parse(getRtdResource("gtfs/routes.txt"));
        // route - join to Agency on the agency_id column
        List<RouteModel> routeModels = Arrays.stream(routes)
                .filter(route -> route.getAgencyId().equals(rtd.getId()))
                .map(route -> new RouteModel(route, rtd))
                .collect(toList());

        Stop[] stops = Stop.parse(getRtdResource("gtfs/stops.txt"), rtd.getTimezone());

        Trip[] trips = Trip.parse(getRtdResource("gtfs/trips.txt"));
        // join to route on route_id
        // join to Calendar on service_id

        StopTime[] stopTimes = StopTime.parse(getRtdResource("gtfs/stop_times.txt"));
        // join to stop on  stop_id column
        // join to trip on trip_id column

        return "index";
    }

}

package net.eightytwenty.rtd.model;

import net.eightytwenty.rtd.gtfs.Agency;
import net.eightytwenty.rtd.gtfs.Route;

/**
 * Model class for linking together GTFS objects
 */
public class RouteModel {

    public RouteModel(
                 Route route,
                 Agency agency) {
        this.agency = agency;
        this.route = route;
    }

    private Agency agency;

    public Route getRoute() {
        return route;
    }

    public Agency getAgency() {
        return agency;
    }

    private Route route;
}

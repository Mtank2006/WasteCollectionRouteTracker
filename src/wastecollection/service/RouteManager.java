package wastecollection.service;

import wastecollection.model.Route;

import java.util.ArrayList;

public class RouteManager {
    private ArrayList<Route> routes;
    public RouteManager() {
        routes = new ArrayList<>();
    }
    public void addRoute(Route route) {
        routes.add(route);
    }
    public ArrayList<Route> getRoutes() {
        return routes;
    }
    public Route findRouteById(int routeId) {
        for (Route i : routes) {
            if (i.getRouteId() == routeId) {
                return i;
            }
        }
        return null;
    }
}

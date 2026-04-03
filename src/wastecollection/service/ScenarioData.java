package wastecollection.service;

import wastecollection.model.*;

import java.util.ArrayList;

public class ScenarioData {
    ArrayList<Area> areas;
    ArrayList<Truck> trucks;
    ArrayList<Route> routes;
    ArrayList<WasteBin> bins;
    public ScenarioData(ArrayList<Area> areas, ArrayList<Truck> trucks, ArrayList<Route> routes, ArrayList<WasteBin> bins) {
        this.areas = areas;
        this.trucks = trucks;
        this.routes = routes;
        this.bins = bins;
    }
    public void setAreas(ArrayList<Area> areas) {
        this.areas = areas;
    }

    public void setTrucks(ArrayList<Truck> trucks) {
        this.trucks = trucks;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public void setBins(ArrayList<WasteBin> bins) {
        this.bins = bins;
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public ArrayList<WasteBin> getBins() {
        return bins;
    }
}

package wastecollection.model;

import java.util.ArrayList;

public class Route {
    private int routeId;
    private Truck assignedTruck;
    private ArrayList<Area> areas;
    public Route(int routeId) {
        this.routeId = routeId;
        areas = new ArrayList<>();
    }
    public void addArea(Area area) {
        areas.add(area);
    }
    public void assignTruck(Truck truck) {
        assignedTruck = truck;
    }
    public int getRouteId() {
        return  routeId;
    }
    public Truck getAssignedTruck() {
        return assignedTruck;
    }
    public ArrayList<Area> getAreas() {
        return areas;
    }
}

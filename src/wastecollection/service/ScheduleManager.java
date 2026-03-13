package wastecollection.service;

import wastecollection.model.Route;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleManager {
    private HashMap<String, ArrayList<Route>> scheduleMap;
    public ScheduleManager() {
        scheduleMap = new HashMap<>();
    }
    public void addRouteToDay(String day, Route route) {
        if (!scheduleMap.containsKey(day)) {
            scheduleMap.put(day, new ArrayList<>());
        }
        scheduleMap.get(day).add(route);
    }
    public ArrayList<Route> getRoutesForDay(String day) {
        if (scheduleMap.containsKey(day)) {
            return scheduleMap.get(day);
        }
        else
            return new ArrayList<>();
    }
    public HashMap<String, ArrayList<Route>> getScheduleMap() {
        return scheduleMap;
    }
}

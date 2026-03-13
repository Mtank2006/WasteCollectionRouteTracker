package wastecollection.service;

import wastecollection.model.Truck;

import java.util.ArrayList;

public class FleetManager {
    private ArrayList<Truck> trucks;
    public FleetManager() {
        trucks = new ArrayList<>();
    }
    public void addTruck(Truck truck) {
        trucks.add(truck);
    }
    public ArrayList<Truck> getTrucks() {
        return trucks;
    }
    public Truck findTruckById(int truckId) {
        for (Truck i : trucks) {
            if (i.getTruckId() == truckId) {
                return i;
            }
        }
        return null;
    }
}

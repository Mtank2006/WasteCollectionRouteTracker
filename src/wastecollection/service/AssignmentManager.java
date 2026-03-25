package wastecollection.service;

import wastecollection.model.Area;
import wastecollection.model.Truck;
import wastecollection.model.WasteBin;
import wastecollection.utils.Helper;

import java.util.ArrayList;

public class AssignmentManager {
    private ArrayList<ArrayList<WasteBin>> truckAssignments;
    public AssignmentManager(int numberOfTrucks) {
        truckAssignments = new ArrayList<>();
        for (int i = 0; i < numberOfTrucks; i ++) {
            truckAssignments.add(new ArrayList<WasteBin>());
        }
    }
    
    public void assignBinsToTrucks(ArrayList<Truck> trucks, ArrayList<WasteBin> bins) {
        for (ArrayList<WasteBin> i : truckAssignments) {
            i.clear();
        }
        for (WasteBin bin : bins) {
            int nearestTruckIndex = -1;
            double shortestDistance = Double.MAX_VALUE;
            Area binArea = bin.getArea();
            for (int i = 0; i < trucks.size(); i++) {
                Truck truck = trucks.get(i);
                Area truckArea = truck.getCurrentArea();
                double distance = Helper.calculateDistance(truckArea.getXCoordinate(), truckArea.getYCoordinate(), binArea.getXCoordinate(), binArea.getYCoordinate());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestTruckIndex = i;
                }
            }
            if (nearestTruckIndex != -1) {
                truckAssignments.get(nearestTruckIndex).add(bin);
            }
        }
    }
    public ArrayList<ArrayList<WasteBin>> getTruckAssignments() {
        return truckAssignments;
    }
}

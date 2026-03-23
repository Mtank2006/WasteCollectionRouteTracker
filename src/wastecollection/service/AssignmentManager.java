package wastecollection.service;

import wastecollection.model.Area;
import wastecollection.model.Truck;
import wastecollection.model.WasteBin;

import java.util.ArrayList;

public class AssignmentManager {
    private ArrayList<ArrayList<WasteBin>> truckAssignments;
    public AssignmentManager(int numberOfTrucks) {
        truckAssignments = new ArrayList<>();
        for (int i = 0; i < numberOfTrucks; i ++) {
            truckAssignments.add(new ArrayList<WasteBin>());
        }
    }
    /*
    public void assignBinTOTrucks(ArrayList<Truck> trucks, ArrayList<WasteBin> bins) {
        for (WasteBin bin : bins) {
            double nearestTruckIndex = -1;
            double shortestDistance = Double.MAX_VALUE;
            Area area = bins.get(bin.getBinId()).getArea();
            for (int )
        }
    }
    */
}

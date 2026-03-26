package wastecollection.utils;

import wastecollection.model.Area;
import wastecollection.model.Truck;
import wastecollection.model.WasteBin;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Helper {
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
//        return x2+y2-x1-y1;
    }
    public void printSection(String title) {
        System.out.println("========== "+title+" ==========");
    }
    public void printCollectionEvent(Truck truck, Area area, WasteBin bin, double wasteAmount) {
//        System.out.println("[COLLECTION EVENT]");
        System.out.println("Truck ID: " + truck.getTruckId());
        System.out.println("Area: " + area.getAreaName());
        System.out.println("Collected: " + wasteAmount);
        System.out.println("Current Load: " + truck.getCurrentLoad()/truck.getCapacity()*100 + "%");
        System.out.println("--------------------------------");
    }
    public void printTruckStatus(Truck truck) {
        if (truck.isFull()) {
            System.out.println("Truck " + truck.getTruckId() + " -> FULL " + "(" + truck.getCurrentLoad() + " / " + truck.getCapacity() + "0");
        }
        else {
            System.out.println("Truck " + truck.getTruckId() + " -> ACTIVE " + "(" + truck.getCurrentLoad() + " / " + truck.getCapacity() + "0");

        }
    }
    public void printSummary (ArrayList<Truck> trucks, int totalBins, int binsCollected, double totalWaste) {
        System.out.println("===== SIMULATION SUMMARY =====" );
        System.out.println("Total Bins: " + totalBins);
        System.out.println("Bins Collected: " + binsCollected);
        System.out.println("Total Waste Collected: " + totalWaste);
        System.out.println();
        System.out.println("Truck Status:");
        for (Truck truck: trucks) {
            printTruckStatus(truck);
        }
        System.out.println("================================");
    }
}

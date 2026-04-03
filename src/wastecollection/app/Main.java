package wastecollection.app;

import wastecollection.model.*;
import wastecollection.service.*;
import wastecollection.utils.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        RouteManager routeManager = new RouteManager();
        BinManager binManager = new BinManager();
        FleetManager fleetManager = new FleetManager();
        ScheduleManager scheduleManager = new ScheduleManager();  // its use has not been implemented. It's not being used in entire project
        Helper helper = new Helper();

        helper.printSection("Program has started");
        System.out.println("Select Scenarios (1-5): ");
        int choice;
        String fileName = null;

        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    fileName = "scenario1.csv";
                    break;
                case 2:
                    fileName = "scenario2.csv";
                    break;
                case 3:
                    fileName = "scenario3.csv";
                    break;
                case 4:
                    fileName = "scenario4.csv";
                    break;
                case 5:
                    fileName = "scenario5.csv";
                    break;
                default:
                    System.out.println("Invalid choice. Scenario out of range. Exiting the program");
                    System.exit(0);
            }
        }
        else {
            System.out.println("Invalid choice. Input was not a number. Exiting the program");
            System.exit(0);
        }

        ScenarioData data = ScenarioLoader.loadScenario(fileName);

        for (WasteBin bin : data.getBins()) {
            binManager.addBin(bin);
        }

        for (Truck truck : data.getTrucks()) {
            fleetManager.addTruck(truck);
        }

        for (Route route : data.getRoutes()) {
            routeManager.addRoute(route);
        }

        double priorityWeight = 1.5;
        double distanceWeight = 1.0;

        System.out.println("Using weights → Priority: " + priorityWeight + " , Distance: " + distanceWeight);
        helper.printSection("INITIALIZATION");

        ArrayList<WasteBin> priorityBins = binManager.getBinsAboveThreshold(80);
        ArrayList<WasteBin> remainingBins = new ArrayList<>(priorityBins);

        helper.printSection("DETECTION");
        for (WasteBin bin : priorityBins) {
            System.out.println("Bin " + bin.getBinId() + " needs collection (" + bin.getFillPercentage() + "% full)");
        }
        System.out.println("These following bins will be prioritised and waste from them will be collected first as they have more waste than the threshold that is 80%");
        System.out.println("After the waste from these bins is collected the waste from other bins will be collected by REASSIGNMENT");
        int totalBins = binManager.getBins().size();
        int binsCollected = 0;
        double totalWaste = 0;

        AssignmentManager assignmentManager = new AssignmentManager(fleetManager.getTrucks().size());
        ArrayList<Truck> activeTrucks = new ArrayList<>(fleetManager.getTrucks());

        while (!remainingBins.isEmpty() && !activeTrucks.isEmpty()) {

            helper.printSection("COLLECTION");

            assignmentManager.assignBinsToTrucks(activeTrucks, remainingBins);
            ArrayList<ArrayList<WasteBin>> assignments = assignmentManager.getTruckAssignments();
            ArrayList<Truck> trucksToRemove = new ArrayList<>();

            boolean anyCollectionHappened = false;
            for (int i = 0; i < activeTrucks.size(); i++) {

                Truck truck = activeTrucks.get(i);
                Route route = truck.getAssignedRoute();
                ArrayList<Area> routeAreas = route.getAreas();

                while (true) {

                    WasteBin bestBin = null;
                    double bestScore = Double.NEGATIVE_INFINITY;

                    for (WasteBin bin : assignments.get(i)) {

                        if (bin.getCurrentFillLevel() > 0 && routeAreas.contains(bin.getArea())) {

                            Area binArea = bin.getArea();
                            double distance = Helper.calculateDistance(
                                    truck.getCurrentArea().getXCoordinate(),
                                    truck.getCurrentArea().getYCoordinate(),
                                    binArea.getXCoordinate(),
                                    binArea.getYCoordinate()
                            );

                            double priority = bin.getFillPercentage();
                            double score = ( priority * priorityWeight ) - ( distance * distanceWeight);

                            if (score > bestScore) {
                                bestScore = score;
                                bestBin = bin;
                            }
                        }
                    }

                    if (bestBin == null) {
                        for (WasteBin bin : assignments.get(i)) {

                            if (bin.getCurrentFillLevel() > 0) {
                                Area binArea = bin.getArea();
                                double distance = Helper.calculateDistance(
                                        truck.getCurrentArea().getXCoordinate(),
                                        truck.getCurrentArea().getYCoordinate(),
                                        binArea.getXCoordinate(),
                                        binArea.getYCoordinate()
                                );

                                double priority = bin.getFillPercentage();
                                double score = ( priority * priorityWeight ) - ( distance * distanceWeight);

                                if (score > bestScore) {
                                    bestScore = score;
                                    bestBin = bin;
                                }
                            }
                        }
                    }

                    // stop if no bin is left
                    if (bestBin == null) {
                        System.out.println("Truck " + truck.getTruckId() + " has no more assigned bins.");
                        break;
                    }

                    double wasteAmount = bestBin.getCurrentFillLevel();

                    // capacity check
                    if (truck.getCurrentLoad() + wasteAmount > truck.getCapacity()) {
                        System.out.println("Truck " + truck.getTruckId() + " is full. Returning to depot.");
                        if (!trucksToRemove.contains(truck)) {
                            trucksToRemove.add(truck);
                        }
                        break;
                    }

                    // collect waste
                    truck.loadWaste(wasteAmount);
                    anyCollectionHappened = true;
                    bestBin.emptyBin();
                    remainingBins.remove(bestBin);
                    Area area = bestBin.getArea();
                    binsCollected++;
                    totalWaste += wasteAmount;
                    helper.printCollectionEvent(truck,area,bestBin,wasteAmount);

                    // update truck location
                    truck.setCurrentArea(area);

                    if (truck.isFull() && !trucksToRemove.contains(truck)) {
                        trucksToRemove.add(truck);
                    }

                }

            }

            activeTrucks.removeAll(trucksToRemove);

            // for preventing deadlock
            if (!anyCollectionHappened) {
                System.out.println("No further collection possible. Exiting");
                break;
            }

            remainingBins.clear();

            for (WasteBin bin : binManager.getBins()) {
                if (bin.getCurrentFillLevel() > 0) {
                    remainingBins.add(bin);
                }
            }
            if (!remainingBins.isEmpty()) {
                helper.printSection("REASSIGNMENT");
            }

        }

        helper.printSummary(fleetManager.getTrucks(),totalBins, binsCollected,totalWaste);

    }
}

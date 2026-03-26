package wastecollection.main;

import wastecollection.model.*;
import wastecollection.service.*;
import wastecollection.utils.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        RouteManager routeManager = new RouteManager();
        BinManager binManager = new BinManager();
        FleetManager fleetManager = new FleetManager();
        ScheduleManager scheduleManager = new ScheduleManager();
        Helper helper = new Helper();

        Area residential = new Area(1,"Residential",2,3);
        Area market = new Area(2,"Market",8,6);

        WasteBin Bin1 = new WasteBin(101,200,new OrganicWaste());
        WasteBin Bin2 = new WasteBin(102,300,new PlasticWaste());
        WasteBin Bin3 = new WasteBin(103,300,new PaperWaste());

        residential.addWasteBin(Bin1);
        residential.addWasteBin(Bin2);
        market.addWasteBin(Bin3);

        binManager.addBin(Bin1);
        binManager.addBin(Bin2);
        binManager.addBin(Bin3);

        Truck truck1 = new Truck(101,500);
        Truck truck2 = new Truck(102,600);

        fleetManager.addTruck(truck1);
        fleetManager.addTruck(truck2);

        Route route1 = new Route(101);
        Route route2 = new Route(102);

        route1.addArea(residential);
        route2.addArea(market);
        route1.assignTruck(truck1);
        route2.assignTruck(truck2);

        truck1.assignRoute(route1);
        truck2.assignRoute(route2);

        routeManager.addRoute(route1);
        routeManager.addRoute(route2);

        scheduleManager.addRouteToDay("Monday",route1);
        scheduleManager.addRouteToDay("Tuesday",route2);

        Bin1.addWaste(160);
        Bin2.addWaste(250);
        Bin3.addWaste(280);

        double priorityWeight = 1.5;
        double distanceWeight = 1.0;

        truck1.setCurrentArea(route1.getAreas().get(0));
        truck2.setCurrentArea(route2.getAreas().get(0));

        System.out.println("Using weights → Priority: " + priorityWeight + " , Distance: " + distanceWeight);
        helper.printSection("INITIALIZATION");

        ArrayList<WasteBin> priorityBins = binManager.getBinsAboveThreshold(80);
        ArrayList<WasteBin> remainingBins = new ArrayList<>(priorityBins);

        helper.printSection("DETECTION");
        for (WasteBin bin : priorityBins) {
            System.out.println("Bin " + bin.getBinId() + " needs collection (" + bin.getFillPercentage() + "% full)");
        }

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
                        break;
                    }

                    // collect waste
                    truck.loadWaste(wasteAmount);
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

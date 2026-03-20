package wastecollection.main;

import wastecollection.model.*;
import wastecollection.service.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        RouteManager routeManager = new RouteManager();
        BinManager binManager = new BinManager();
        FleetManager fleetManager = new FleetManager();
        ScheduleManager scheduleManager = new ScheduleManager();
        Area residential = new Area(1,"Residential",2,3);
        Area market = new Area(2,"market",8,6);
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
        routeManager.addRoute(route1);
        routeManager.addRoute(route2);
        scheduleManager.addRouteToDay("Monday",route1);
        scheduleManager.addRouteToDay("Tuesday",route2);
        Bin1.addWaste(160);
        Bin2.addWaste(250);
        Bin3.addWaste(280);
        truck1.setCurrentArea(route1.getAreas().get(0));
        truck2.setCurrentArea(route2.getAreas().get(0));
//        ArrayList<WasteBin> priorityBins = binManager.getFullBins();
        ArrayList<WasteBin> priorityBins = binManager.getBinsAboveThreshold(80);
        for (WasteBin bin : priorityBins) {
//            System.out.println("Full Bin ID: " + bin.getBinId());
            System.out.println(
                    "Bin " + bin.getBinId() +
                            " needs collection (" + bin.getFillPercentage() + "% full)"
            );
        }
//        Without priority given to bins, ie sequentially
//        for (Route route : routeManager.getRoutes()) {
//            Truck truck = route.getAssignedTruck();
//            for (Area area : route.getAreas()) {
//                for (WasteBin bin : area.getWasteBins()) {
//                    if (bin.isFull()) {
//                        truck.loadWaste(bin.getCurrentFillLevel());
//                        bin.emptyBin();
//                        System.out.println(
//                                "Truck " + truck.getTruckId() +
//                                        " collected waste from Bin " + bin.getBinId() +
//                                        " in Area " + area.getAreaName()
//                        );
//                    }
//                }
//            }
//        }
//        With priority given to bins;
        double threshold = 80;

// get bins sorted by priority
        priorityBins = binManager.getBinsSortedByPriority();

        for (Truck truck : fleetManager.getTrucks()) {

            for (WasteBin bin : priorityBins) {

                // only collect bins that meet the threshold
                if (bin.getFillPercentage() >= threshold && bin.getCurrentFillLevel() > 0) {

                    double wasteAmount = bin.getCurrentFillLevel();

                    // check truck capacity
                    if (truck.getCurrentLoad() + wasteAmount <= truck.getCapacity()) {

                        truck.loadWaste(wasteAmount);
                        bin.emptyBin();

                        System.out.println(
                                "Truck " + truck.getTruckId() +
                                        " collected waste from Bin " + bin.getBinId() +
                                        " (" + wasteAmount + " units)"
                        );
                    }

                    // stop using this truck if it becomes full
                    if (truck.isFull()) {
                        System.out.println(
                                "Truck " + truck.getTruckId() +
                                        " is full. Returning to depot."
                        );
                        break;
                    }
                }
            }
        }


    }
}

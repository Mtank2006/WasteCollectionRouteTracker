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
        Area residential = new Area(1,"Residential");
        Area Market = new Area(2,"Market");
        WasteBin Bin1 = new WasteBin(101,200,new OrganicWaste());
        WasteBin Bin2 = new WasteBin(102,300,new PlasticWaste());
        WasteBin Bin3 = new WasteBin(103,300,new PaperWaste());

        residential.addWasteBin(Bin1);
        residential.addWasteBin(Bin2);
        Market.addWasteBin(Bin3);
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
        route2.addArea(Market);
        route1.assignTruck(truck1);
        route2.assignTruck(truck2);
        routeManager.addRoute(route1);
        routeManager.addRoute(route2);
        scheduleManager.addRouteToDay("Monday",route1);
        scheduleManager.addRouteToDay("Tuesday",route2);
        Bin1.addWaste(40);
        Bin2.addWaste(300);
        Bin3.addWaste(900);
        ArrayList<WasteBin> fullBins = binManager.getFullBins();

        for (WasteBin bin : fullBins) {
            System.out.println("Full Bin ID: " + bin.getBinId());
        }

        for (Route route : routeManager.getRoutes()) {
            Truck truck = route.getAssignedTruck();
            for (Area area : route.getAreas()) {
                for (WasteBin bin : area.getWasteBins()) {
                    if (bin.isFull()) {
                        truck.loadWaste(bin.getCurrentFillLevel());
                        System.out.println(
                                "Truck " + truck.getTruckId() +
                                        " collected waste from Bin " + bin.getBinId() +
                                        " in Area " + area.getAreaName()
                        );
                    }
                }
            }
        }

    }
}

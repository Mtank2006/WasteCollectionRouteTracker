package wastecollection.service;

import wastecollection.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ScenarioLoader {

    public static ScenarioData loadScenario(String fileName) {

        ArrayList<Area> areas = new ArrayList<>();
        ArrayList<Truck> trucks = new ArrayList<>();
        ArrayList<Route> routes = new ArrayList<>();
        ArrayList<WasteBin> bins = new ArrayList<>();

        // temporary storage for linking
        ArrayList<int[]> routeAreaMap = new ArrayList<>(); // {routeId, areaId}
        ArrayList<int[]> truckRouteMap = new ArrayList<>(); // {truckId, routeId}
        ArrayList<int[]> binAreaMap = new ArrayList<>(); // {binIndex, areaId}

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] tokens = line.split(",");

                String type = tokens[0];

                switch (type) {

                    case "AREA":
                        int areaId = Integer.parseInt(tokens[1]);
                        String name = tokens[2];
                        double x = Double.parseDouble(tokens[3]);
                        double y = Double.parseDouble(tokens[4]);

                        Area area = new Area(areaId, name, x, y);
                        areas.add(area);
                        break;

                    case "TRUCK":
                        int truckId = Integer.parseInt(tokens[1]);
                        double capacity = Double.parseDouble(tokens[2]);
                        int routeId = Integer.parseInt(tokens[3]);

                        Truck truck = new Truck(truckId, capacity);
                        trucks.add(truck);

                        truckRouteMap.add(new int[]{truckId, routeId});
                        break;

                    case "ROUTE":
                        int rId = Integer.parseInt(tokens[1]);
                        int rAreaId = Integer.parseInt(tokens[2]);

                        Route route = findRoute(routes, rId);
                        if (route == null) {
                            route = new Route(rId);
                            routes.add(route);
                        }

                        routeAreaMap.add(new int[]{rId, rAreaId});
                        break;

                    case "BIN":
                        int binId = Integer.parseInt(tokens[1]);
                        double binCapacity = Double.parseDouble(tokens[2]);
                        double fill = Double.parseDouble(tokens[3]);
                        String wasteType = tokens[4];
                        int areaRef = Integer.parseInt(tokens[5]);

                        Waste waste = createWaste(wasteType);

                        WasteBin bin = new WasteBin(binId, binCapacity, waste);
                        bin.addWaste(fill);

                        bins.add(bin);

                        binAreaMap.add(new int[]{bins.size() - 1, areaRef});
                        break;
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // assign bins to areas
        for (int[] entry : binAreaMap) {
            int binIndex = entry[0];
            int areaId = entry[1];

            Area area = findArea(areas, areaId);
            if (area != null) {
                area.addWasteBin(bins.get(binIndex));
            }
        }

        // assign routes to areas
        for (int[] entry : routeAreaMap) {
            int routeId = entry[0];
            int areaId = entry[1];

            Route route = findRoute(routes, routeId);
            Area area = findArea(areas, areaId);

            if (route != null && area != null) {
                route.addArea(area);
            }
        }

        // assign trucks to routes
        for (int[] entry : truckRouteMap) {
            int truckId = entry[0];
            int routeId = entry[1];

            Truck truck = findTruck(trucks, truckId);
            Route route = findRoute(routes, routeId);

            if (truck != null && route != null) {
                route.assignTruck(truck);
                truck.assignRoute(route);

                if (!route.getAreas().isEmpty()) {
                    truck.setCurrentArea(route.getAreas().get(0));
                }
            }
        }

        // return data
        return new ScenarioData(areas, trucks, routes, bins);
    }

    private static Area findArea(ArrayList<Area> areas, int id) {
        for (Area a : areas) {
            if (a.getAreaId() == id) return a;
        }
        return null;
    }

    private static Route findRoute(ArrayList<Route> routes, int id) {
        for (Route r : routes) {
            if (r.getRouteId() == id) return r;
        }
        return null;
    }

    private static Truck findTruck(ArrayList<Truck> trucks, int id) {
        for (Truck t : trucks) {
            if (t.getTruckId() == id) return t;
        }
        return null;
    }

    private static Waste createWaste(String type) {
        switch (type) {
            case "Organic":
                return new OrganicWaste();
            case "Plastic":
                return new PlasticWaste();
            case "Paper":
                return new PaperWaste();
            case "Metal":
                return new MetalWaste();
            default:
                return new OrganicWaste();
        }
    }
}
package wastecollection.model;

public class Truck {
    private int truckId;
    private double capacity;
    private double currentLoad;
    private Route assignedRoute;
    private Area currentArea;
    public Truck(int truckId, double capacity) {
        this.truckId = truckId;
        this.capacity = capacity;
        this.currentLoad = 0;
    }
    public void loadWaste(double amount) {
        currentLoad = currentLoad + amount;
    }
    public boolean isFull() {
        return currentLoad >= capacity;
    }
    public void assignRoute(Route route) {
        assignedRoute = route;
    }
    public int getTruckId() {
       return truckId;
    }
    public double getCapacity() {
        return capacity;
    }
    public double getCurrentLoad() {
        return currentLoad;
    }
    public Route getAssignedRoute() {
        return assignedRoute;
    }
    public void collectWasteFromBin(WasteBin bin) {
        if (getCapacity() >= getCurrentLoad() + bin.getCurrentFillLevel()) {
            loadWaste(bin.getCurrentFillLevel());
            bin.emptyBin();
        }
    }
    public void setCurrentArea(Area area) {
        currentArea = area;
    }

    public Area getCurrentArea() {
        return currentArea;
    }
}

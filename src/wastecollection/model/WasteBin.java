package wastecollection.model;

public class WasteBin {
    private int binId;
    private double capacity;
    private double currentFillLevel;
    private Waste wasteType;
    private Area area;
    public WasteBin(int binId, double capacity, Waste wasteType) {
        this.binId = binId;
        this.capacity = capacity;
        this.wasteType = wasteType;
        this.currentFillLevel = 0;
    }
    public void addWaste(double amount) {
        currentFillLevel = currentFillLevel + amount;
    }
    public boolean isFull() {
        return currentFillLevel >= capacity;
    }
    public int getBinId() {
        return binId;
    }
    public double getCapacity() {
        return capacity;
    }
    public double getCurrentFillLevel() {
        return currentFillLevel;
    }
    public Waste getWasteType() {
        return wasteType;
    }
    public void emptyBin() { currentFillLevel = 0; }
    public double getFillPercentage() { return (currentFillLevel/capacity)*100; }
    public void setArea(Area area) { this.area = area; }
    public Area getArea() { return area; }
}
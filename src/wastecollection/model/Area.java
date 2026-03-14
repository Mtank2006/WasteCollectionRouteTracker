package wastecollection.model;

import java.util.ArrayList;

public class Area {

    private int areaId;
    private String areaName;
    private ArrayList<WasteBin> wasteBins;
    private double xCoordinate;
    private double yCoordinate;
    // Constructor
    public Area(int areaId, String areaName, double xCoordinate, double yCoordinate) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.wasteBins = new ArrayList<>();
    }

    // Add a waste bin to the area
    public void addWasteBin(WasteBin bin) {
        wasteBins.add(bin); bin.setArea(this);
    }

    // Get all waste bins in the area
    public ArrayList<WasteBin> getWasteBins() {
        return wasteBins;
    }

    // Get area ID
    public int getAreaId() {
        return areaId;
    }

    // Get area name
    public String getAreaName() {
        return areaName;
    }

    public double getxCoordinate() { return xCoordinate; }

    public double getyCoordinate() { return yCoordinate; }
}
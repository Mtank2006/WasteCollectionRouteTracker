package wastecollection.model;

import java.util.ArrayList;

public class Area {

    private int areaId;
    private String areaName;
    private ArrayList<WasteBin> wasteBins;
    private double xCoordinate;
    private double yCoordinate;

    public Area(int areaId, String areaName, double xCoordinate, double yCoordinate) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.wasteBins = new ArrayList<>();
    }
    public void addWasteBin(WasteBin bin) {
        wasteBins.add(bin);
        bin.setArea(this);
    }
    public ArrayList<WasteBin> getWasteBins() {
        return wasteBins;
    }
    public int getAreaId() {
        return areaId;
    }
    public String getAreaName() {
        return areaName;
    }
    public double getXCoordinate() { return xCoordinate; }
    public double getYCoordinate() { return yCoordinate; }
}
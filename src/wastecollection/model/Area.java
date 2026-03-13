package wastecollection.model;

import java.util.ArrayList;

public class Area {

    private int areaId;
    private String areaName;
    private ArrayList<WasteBin> wasteBins;

    // Constructor
    public Area(int areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.wasteBins = new ArrayList<>();
    }

    // Add a waste bin to the area
    public void addWasteBin(WasteBin bin) {
        wasteBins.add(bin);
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
}
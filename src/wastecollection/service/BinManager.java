package wastecollection.service;

import wastecollection.model.Area;
import wastecollection.model.WasteBin;
import wastecollection.utils.Helper;

import java.util.ArrayList;

public class BinManager {
    private ArrayList<WasteBin> bins;
    public BinManager() {
        bins = new ArrayList<>();
    }
    public void addBin(WasteBin bin) {
        bins.add(bin);
    }
    public ArrayList<WasteBin> getBins() {
        return bins;
    }
    public ArrayList<WasteBin> getFullBins() {
        ArrayList<WasteBin> fullBins = new ArrayList<>();
        for (WasteBin i : bins) {
            if(i.isFull()) {
                fullBins.add(i);
            }
        }
        return fullBins;
    }
    public ArrayList<WasteBin> getBinsAboveThreshold (double thresholdPercentage) {
        ArrayList<WasteBin> thresholdBin = new ArrayList<>();
        for (WasteBin i : bins) {
            if(i.getFillPercentage() >= thresholdPercentage) {
                thresholdBin.add(i);
            }
        }
        return thresholdBin;
    }
    public Area findNearestAreaWithUrgentBins(Area currentArea, double threshold) {
        Area nearestArea = null;
        double shortestDistance = Double.MAX_VALUE;
        for (WasteBin i : bins) {
            if(i.getFillPercentage() >= threshold) {
                Helper helper = new Helper();
                Area area = bin.getArea();
                double distance = helper.calculateDistance(currentArea.getxCoordinate(),currentArea.getyCoordinate(),area.getxCoordinate(),area.getyCoordinate());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestArea = area;
                }
            }
            return nearestArea;
        }
    }
}

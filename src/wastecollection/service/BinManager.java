package wastecollection.service;

import wastecollection.model.WasteBin;

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
//    public ArrayList<WasteBin> getBinsAboveThreshold (double thresholdPercentage) {
//
//    }
}

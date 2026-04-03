package wastecollection.model;

public abstract class Waste {
    public String wasteCategory;
    public Waste(String wasteCategory) {
        this.wasteCategory = wasteCategory;
    }
    public String getWasteCategory() {
        return wasteCategory;
    }
}

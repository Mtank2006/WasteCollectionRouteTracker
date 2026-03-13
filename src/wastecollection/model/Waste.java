package wastecollection.model;

abstract class Waste {
    private String wasteCategory;
    public Waste(String wasteCategory) {
        this.wasteCategory = wasteCategory;
    }
    public String getWasteCategory() {
        return wasteCategory;
    }
}

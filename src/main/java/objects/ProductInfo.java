package objects;

public class ProductInfo {
    private int StockItemID;
    private String StockItemName;
    private double UnitPrice;
    private double RecommendedRetailPrice;
    private int TypicalWeightPerUnit;
    private int QuantityPerOuter;
    private String Location;

    public int getStockItemID() {
        return StockItemID;
    }

    public void setStockItemID(int stockItemID) {
        StockItemID = stockItemID;
    }

    public String getStockItemName() {
        if(StockItemName.length() <=33) {
            return StockItemName;
        } else
            return StockItemName.substring(0, 33)+ "...";
    }

    public void setStockItemName(String stockItemName) {
        StockItemName = stockItemName;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getRecommendedRetailPrice() {
        return RecommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(double recommendedRetailPrice) {
        RecommendedRetailPrice = recommendedRetailPrice;
    }

    public int getTypicalWeightPerUnit() {
        return TypicalWeightPerUnit;
    }

    public void setTypicalWeightPerUnit(int typicalWeightPerUnit) {
        TypicalWeightPerUnit = typicalWeightPerUnit;
    }

    public int getQuantityPerOuter() {
        return QuantityPerOuter;
    }

    public void setQuantityPerOuter(int quantityPerOuter) {
        QuantityPerOuter = quantityPerOuter;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @Override
    public String toString() {
        return
                StockItemID + " " +
                        StockItemName + " " +
                        UnitPrice + " " +
                        RecommendedRetailPrice + " " +
                        TypicalWeightPerUnit + " " +
                        QuantityPerOuter + " " +
                        Location
                ;
    }

}

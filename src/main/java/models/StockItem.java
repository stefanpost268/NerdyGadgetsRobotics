package models;

public class StockItem extends BaseModel<StockItem> {
    public Integer StockItemID;
    public String StockItemName;
    public Integer UnitPrice;
    public Integer RecommendedRetailPrice;
    public Integer TypicalWeightPerUnit;
    public Integer QuantityPerOuter;
    public String Size;

    @Override
    public String[] fillable() {
        return new String[] {
            "StockItemID",
            "StockItemName",
            "UnitPrice",
            "RecommendedRetailPrice",
            "TypicalWeightPerUnit",
            "QuantityPerOuter",
            "Size"
        };
    }

    @Override
    public String getTableName() {
        return "stockitems";
    }

    @Override
    public StockItem createInstance() {
        return new StockItem();
    }
}
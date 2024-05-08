package models;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "stockitems")
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int StockItemID;

    @Column(length = 100, nullable = false, unique = true)
    private String stockItemName;

    @Column(nullable = true)
    private double UnitPrice;

    @Column(nullable = true)
    private double RecommendedRetailPrice;

    @Column(nullable = false)
    private double TypicalWeightPerUnit;

    @Column(length = 11, nullable = false)
    private int QuantityPerOuter;

    @Column(length = 20, nullable = false)
    private String Size;

    public List<String> getFieldNames() {
        return Arrays.asList("StockItemID", "StockItemName", "UnitPrice", "RecommendedRetailPrice", "TypicalWeightPerUnit", "QuantityPerOuter", "Size");
    }

    public List<Object> getFieldValues() {
        return Arrays.asList(this.StockItemID, this.stockItemName, this.UnitPrice, this.RecommendedRetailPrice, this.TypicalWeightPerUnit, this.QuantityPerOuter, this.Size);
    }
}

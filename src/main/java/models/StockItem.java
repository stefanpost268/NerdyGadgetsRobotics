package models;

import jakarta.persistence.*;
import java.util.Arrays;
import java.math.BigDecimal;
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
    private BigDecimal UnitPrice;

    @Column(nullable = true)
    private BigDecimal RecommendedRetailPrice;

    @Column(nullable = false)
    private double TypicalWeightPerUnit;

    @Column(length = 11, nullable = false)
    private int QuantityPerOuter;

    @Column(length = 20, nullable = false)
    private String Size;

    public List<String> getFieldNames() {
        return Arrays.asList("StockItemID", "StockItemName", "UnitPrice", "RecommendedRetailPrice", "TypicalWeightPerUnit", "QuantityPerOuter", "Size");
    }
    
    public int getStockItemID() {
        return StockItemID;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public double getTypicalWeightPerUnit() {
        return TypicalWeightPerUnit;
    }
}

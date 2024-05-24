package models;

import jakarta.persistence.*;

import java.math.BigDecimal;
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

    @ManyToOne
    @JoinColumn(name = "UnitPackageID", nullable = false)
    private PackageType UnitPackageID;

    @Column(nullable = true)
    private double UnitPrice;

    @Column(nullable = true)
    private double RecommendedRetailPrice;

    @Column(nullable = false)
    private BigDecimal TypicalWeightPerUnit;

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

    public BigDecimal getTypicalWeightPerUnit() {
        return TypicalWeightPerUnit;
    }

    public PackageType getUnitPackageID() {
        return UnitPackageID;
    }

    public void setUnitPackageID(PackageType unitPackageID) {
        UnitPackageID = unitPackageID;
    }
}

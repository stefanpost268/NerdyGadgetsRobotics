package models;

import jakarta.persistence.*;

import java.math.BigDecimal;
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
    private BigDecimal TypicalWeightPerUnit;

    @Column(length = 11, nullable = false)
    private int QuantityPerOuter;
    @Column(length = 20, nullable = false)
    private String Size;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "StockItemID", referencedColumnName = "StockItemID")
    private StockItemHolding stockItemHolding;

    public StockItemHolding getStockItemHolding() {
        return stockItemHolding;
    }

    @Deprecated
    public List<String> getFieldNames() {
        return Arrays.asList("StockItemID", "StockItemName", "UnitPrice", "RecommendedRetailPrice", "TypicalWeightPerUnit", "quantityonhand", "Size");
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

    public BigDecimal getUnitPrice() {
        return UnitPrice;
    }

    public BigDecimal getRecommendedRetailPrice() {
        return RecommendedRetailPrice;
    }

    public int getQuantityPerOuter() {
        return QuantityPerOuter;
    }

    public String getSize() {
        return Size;
    }
}


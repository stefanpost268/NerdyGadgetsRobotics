package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.*;

@Entity
@Table(name = "stockitems")
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockItemId;

    @Column(length = 100, nullable = false, unique = true)
    private String stockItemName;

    @Column(length = 50)
    private String brand;

    public String getStockItemName() {
        return stockItemName;
    }
}

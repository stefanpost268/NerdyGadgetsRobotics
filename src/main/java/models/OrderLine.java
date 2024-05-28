package models;

import jakarta.persistence.*;

@Entity
@Table(name = "orderlines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderLineID;

    @ManyToOne
    @JoinColumn(name = "StockItemID", nullable = false)
    private StockItem stockItem;

    @Column(nullable = false)
    private String Description;

    @Column(nullable = false)
    private int PackageTypeID;

    @Column(nullable = false)
    private int Quantity;

    @Column()
    private Double UnitPrice;

    @Column(nullable = false)
    private double TaxRate;

    @Column(nullable = false)
    private int PickedQuantity;

    public int getOrderLineID() {
        return OrderLineID;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public int getQuantity() {
        return Quantity;
    }
}

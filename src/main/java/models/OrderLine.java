package models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "orderlines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderLineID;

    @ManyToOne
    @JoinColumn(name = "OrderId", referencedColumnName = "OrderID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "StockItemID", nullable = false)
    private StockItem stockItem;

    @Column(nullable = false)
    private String Description;

    @ManyToOne
    @JoinColumn(name = "PackageTypeID", nullable = false)
    private PackageType PackageTypeID;

    @Column(length = 11)
    private int Quantity;

    @Column(nullable = false)
    private final double TaxRate = 21.0;

    @Column(nullable = false, length = 11)
    private int pickedQuantity;

    @ManyToOne
    @JoinColumn(name = "LastEditedBy", nullable = false)
    private People lastEditedBy;

    @Column(nullable = false)
    private Date LastEditedWhen;

    public StockItem getStockItem() {
        return stockItem;
    }

    public int getQuantity() {
        return Quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }


    public void setDescription(String description) {
        Description = description;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getTaxRate() {
        return TaxRate;
    }

    public int getPickedQuantity() {
        return pickedQuantity;
    }

    public void setPickedQuantity(int pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public People getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(People lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Date getLastEditedWhen() {
        return LastEditedWhen;
    }

    public void setLastEditedWhen(Date lastEditedWhen) {
        LastEditedWhen = lastEditedWhen;
    }

    public void setPackageTypeID(PackageType packageTypeID) {
        PackageTypeID = packageTypeID;
    }
}

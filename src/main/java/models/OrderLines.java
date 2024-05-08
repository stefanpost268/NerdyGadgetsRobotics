package models;

import jakarta.persistence.*;

@Entity
@Table(name = "orderlines")
public class OrderLines {

    @Id
    @Column(length = 11)
    private int OrderLineID;

    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "StockItemID", nullable = false)
    private StockItem stockItem;

    @Column(length = 11)
    private int Quantity;
}

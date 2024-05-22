package models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "orderlines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderLineID;

    @Column(nullable = false)
    private int OrderID;

    @Column(nullable = false)
    private int StockItemID;

    @Column(nullable = false)
    private String Description;

    @Column(nullable = false)
    private int PackageTypeID;

    @Column(nullable = false)
    private int Quantity;

    @Column()
    private double UnitPrice;

    @Column(nullable = false)
    private double TaxRate;

    @Column(nullable = false)
    private int PickedQuantity;

}

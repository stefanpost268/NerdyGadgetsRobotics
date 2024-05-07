package models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderID;

    @Column(nullable = false)
    private Date ExpectedDeliveryDate;

    @Column(nullable = false)
    private int CustomerID;

    @Column(nullable = false)
    private int SalespersonPersonID;

    @Column(nullable = false)
    private int ContactPersonID;

    @Column(nullable = false)
    private int PickedByPersonID;

    @Column(nullable = true)
    private String Comments;

    @Column(nullable = true)
    private String DeliveryInstructions;

    @Column(nullable = true)
    private String InternalComments;

    public int getOrderID() {
        return OrderID;
    }

    public Date getExpectedDeliveryDate() {
        return ExpectedDeliveryDate;
    }
}

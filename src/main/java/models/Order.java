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

    @ManyToOne()
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name = "SalespersonPersonID", nullable = false)
    private People Salesperson;

    @ManyToOne()
    @JoinColumn(name = "ContactPersonID", nullable = false)
    private People ContactPerson;

    @ManyToOne()
    @JoinColumn(name = "PickedByPersonID")
    private People PickedByPerson;

    @ManyToOne()
    @JoinColumn(name = "LastEditedBy", nullable = false)
    private People LastEditedBy;

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
    public Customer getCustomer() {
        return customer;
    }
    public People getContactPerson() {
        return ContactPerson;
    }
    public People getSalesperson() {
        return Salesperson;
    }

    public People getPickedByPerson() {
        return PickedByPerson;
    }

    public String getComments() {
        return Comments;
    }

    public String getDeliveryInstructions() {
        return DeliveryInstructions;
    }

    public String getInternalComments() {
        return InternalComments;
    }

    public People getLastEditedBy() {
        return LastEditedBy;
    }
}

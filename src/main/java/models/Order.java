package models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderID;

    @Column(nullable = false)
    private LocalDate ExpectedDeliveryDate;

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

    @Column(nullable = true)
    private String Comments;

    @Column()
    private String DeliveryInstructions;

    @Column()
    private String InternalComments;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderLines> orderLines;

    public int getOrderID() {
        return OrderID;
    }
    public LocalDate getExpectedDeliveryDate() {
        return ExpectedDeliveryDate;
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

    public String getInternalComments() {
        return InternalComments;
    }

    public String getDeliveryInstructions() {
        return DeliveryInstructions;
    }

    public List<OrderLines> getOrderLines() {
        return orderLines;
    }

    public String getOrderState() {
        return "NOT IMPLEMENTED";
    }

    @Column()
    private String OrderDate;

    @Column()
    private String Status;

    public int getOrderID() {
        return OrderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getStatus() {
        return Status;
    }

    public Object[] toObjectArray() {

        return new Object[] {getOrderID(), getCustomer().getCustomerName(), getStatus(), getOrderLines().size(), getOrderDate()};
    }

}

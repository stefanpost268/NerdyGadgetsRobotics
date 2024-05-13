package models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    enum OrderEnum {
        Open,
        InProgress,
        Done,
        Error
    }

    public String getStatus() {
        return Status;
    }

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

    @Column(nullable = true)
    private String DeliveryInstructions;

    @Column(nullable = true)
    private String InternalComments;

    @Column(name = "Status", nullable = false)
    private String Status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderLines> orderLines;

    public List<String> getFieldNames() {
        return Arrays.asList("OrderID", "ExpectedDeliveryDate", "Customer", "ContactPerson", "Salesperson", "PickedByPerson", "Comments", "InternalComments", "DeliveryInstructions", "OrderLines");
    }

    public int getOrderID() {
        return OrderID;
    }
    public LocalDate getExpectedDeliveryDate() {
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
}

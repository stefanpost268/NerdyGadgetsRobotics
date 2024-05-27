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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderID;

    @Column(nullable = false)
    private LocalDate ExpectedDeliveryDate;

    @Column(nullable = false)
    private Date PickingCompletedWhen;

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

    @Column(name = "Status", nullable = false)
    private String Status;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderID")
    private List<OrderLine> orderLine;

    public List<String> getFieldNames() {
        return Arrays.asList("OrderID", "ExpectedDeliveryDate", "Customer", "ContactPerson", "Salesperson", "PickedByPerson", "Comments", "InternalComments", "DeliveryInstructions", "OrderLines");
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

    public List<OrderLine> getOrderLines() {
        return orderLine;
    }

    public String getOrderState() {
        return "NOT IMPLEMENTED";
    }

    @Column()
    private String OrderDate;

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

    public Date getPickingCompletedWhen() {
        return PickingCompletedWhen;
    }

    public Object[] toObjectArray() {

        return new Object[] {getOrderID(), getCustomer().getCustomerName(), getStatus(), getOrderLines().size(), getOrderDate()};
    }


}

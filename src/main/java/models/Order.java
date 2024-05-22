package models;


import jakarta.persistence.*;
import java.time.LocalDate;

import java.util.Arrays;
import java.util.List;

import java.time.format.DateTimeFormatter;
import java.util.Locale;



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

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderLines> orderLines;

    public List<String> getFieldNames() {
        return Arrays.asList("OrderID", "ExpectedDeliveryDate", "Customer", "ContactPerson", "Salesperson", "PickedByPerson", "Comments", "InternalComments", "DeliveryInstructions", "OrderLines");
    }

    public LocalDate getExpectedDeliveryDate() {

        return ExpectedDeliveryDate;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("nl", "NL"));
        String formattedDate = ExpectedDeliveryDate.format(formatter);
        return formattedDate;
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

    @Column()
    private String OrderDate;

    public int getOrderID() {
        return OrderID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object[] toObjectArray() {

        return new Object[] {getOrderID(), getCustomer().getCustomerName(), getStatus(), getOrderLines().size(), getOrderDate()};
    }

}

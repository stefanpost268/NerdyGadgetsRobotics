package models;


import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.util.Set;

@Entity
@DynamicInsert
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

    @ManyToOne()
    @JoinColumn(name = "LastEditedBy", nullable = false)
    private People LastEditedBy;

    @Column(nullable = true)
    private String Comments;

    @Column()
    private String DeliveryInstructions;

    @Column()
    private String InternalComments;

    @OneToMany(mappedBy = "orderID", fetch = FetchType.EAGER)
    private List<OrderLines> orderLines;

    public Order() {
    }

    public Order(Customer customer, People salesperson, People pickedByPerson, People contactPerson, Date orderDate, LocalDate expectedDeliveryDate, boolean isUnderSupplyBackordered, String comments, String deliveryInstructions, String internalComments, People lastEditedBy, Date lastEditedWhen, String orderState) {
        this.customer = customer;
        Salesperson = salesperson;
        PickedByPerson = pickedByPerson;
        ContactPerson = contactPerson;
        OrderDate = orderDate;
        ExpectedDeliveryDate = expectedDeliveryDate;
        IsUnderSupplyBackordered = isUnderSupplyBackordered;
        Comments = comments;
        DeliveryInstructions = deliveryInstructions;
        InternalComments = internalComments;
        LastEditedBy = lastEditedBy;
        LastEditedWhen = lastEditedWhen;
        Status = orderState;
    }

    @Column(nullable = false)
    private Date OrderDate;

    @Column(nullable = false)
    private boolean IsUnderSupplyBackordered;

    @Column(nullable = false)
    private Date LastEditedWhen;

    @Column(nullable = false)
    private String Status;

    public Order(Customer customer, People salesperson, People pickedByPerson, People contactPerson, Date orderDate, LocalDate expectedDeliveryDate, boolean isUnderSupplyBackordered, String comments, String deliveryInstructions, String internalComments, People lastEditedBy, Date lastEditedWhen, String orderState) {
        this.customer = customer;
        Salesperson = salesperson;
        PickedByPerson = pickedByPerson;
        ContactPerson = contactPerson;
        OrderDate = orderDate;
        ExpectedDeliveryDate = expectedDeliveryDate;
        IsUnderSupplyBackordered = isUnderSupplyBackordered;
        Comments = comments;
        DeliveryInstructions = deliveryInstructions;
        InternalComments = internalComments;
        LastEditedBy = lastEditedBy;
        LastEditedWhen = lastEditedWhen;
        Status = orderState;
    }

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
    public List<OrderLines> getOrderLines() {
        return orderLines;
    }
    public String getOrderState() {
        return "NOT IMPLEMENTED";
    }

    public int getOrderID() {
        return OrderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Object[] toObjectArray() {

        return new Object[] {getOrderID(), getCustomer().getCustomerName(), getStatus(), getOrderLines().size(), getOrderDate()};
    }

    public People getLastEditedBy() {
        return LastEditedBy;
    }
    public Date getOrderDate() {
        return OrderDate;
    }
    public boolean isUnderSupplyBackordered() {
        return IsUnderSupplyBackordered;
    }
    public Date getLastEditedWhen() {
        return LastEditedWhen;
    }
    public String getStatus() {
        return Status;
    }
    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }
    public void setInternalComments(String internalComments) {
        InternalComments = internalComments;
    }
    public void setDeliveryInstructions(String deliveryInstructions) {
        DeliveryInstructions = deliveryInstructions;
    }
    public void setComments(String comments) {
        Comments = comments;
    }
    public void setLastEditedBy(People lastEditedBy) {
        LastEditedBy = lastEditedBy;
    }
    public void setPickedByPerson(People pickedByPerson) {
        PickedByPerson = pickedByPerson;
    }
    public void setContactPerson(People contactPerson) {
        ContactPerson = contactPerson;
    }
    public void setSalesperson(People salesperson) {
        Salesperson = salesperson;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        ExpectedDeliveryDate = expectedDeliveryDate;
    }
    public void setUnderSupplyBackordered(boolean underSupplyBackordered) {
        IsUnderSupplyBackordered = underSupplyBackordered;
    }
    public void setLastEditedWhen(Date lastEditedWhen) {
        LastEditedWhen = lastEditedWhen;
    }
    public void setStatus(String status) {
        Status = status;
    }
}

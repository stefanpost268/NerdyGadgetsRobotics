package models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderID;

    @Column(nullable = false)
    private int SalespersonPersonID;

    @Column(nullable = false)
    private int ContactPersonID;

    @Column(nullable = false)
    private int PickedByPersonID;

    @Column()
    private String Comments;

    @Column()
    private String DeliveryInstructions;

    @Column()
    private String InternalComments;

    @Column()
    private String OrderDate;

    @Column()
    private String Status;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderID")
    private List<OrderLine> orderLines;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    public int getOrderID() {
        return OrderID;
    }

    public Customer getCustomer() {
        return customer;
    }


    public int getSalespersonPersonID() {
        return SalespersonPersonID;
    }

    public int getContactPersonID() {
        return ContactPersonID;
    }

    public int getPickedByPersonID() {
        return PickedByPersonID;
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

    public List<OrderLine> getOrderLines() {
        return orderLines;
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

    @Override
    public String toString() {
        return "Order{" +
                "OrderID=" + OrderID +
                '}';
    }
}

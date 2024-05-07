package models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private int CustomerID;

    @Column(name = "CustomerName")
    private String CustomerName;

    @Column(name = "PhoneNumber")
    private String PhoneNumber;

    @Column(name = "DeliveryPostalCode")
    private String DeliveryPostalCode;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders;

    public int getCustomerID() {
        return CustomerID;
    }
}

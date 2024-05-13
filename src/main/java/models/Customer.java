package models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CustomerID;

    @Column(name = "CustomerName", unique = true)
    private String CustomerName;

    @Column(name = "PhoneNumber")
    private String PhoneNumber;

    @Column(name = "DeliveryPostalCode")
    private String DeliveryPostalCode;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders;

    public String getCustomerName() {
        return CustomerName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getDeliveryPostalCode() {
        return DeliveryPostalCode;
    }
}

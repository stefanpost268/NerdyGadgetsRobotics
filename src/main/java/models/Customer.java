package models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CustomerID;

    @Column(nullable = false)
    private String CustomerName;

    @Column(nullable = false)
    private int BillToCustomerID;

    @Column(nullable = false)
    private int CustomerCategoryID;

    @Column(nullable = false)
    private int BuyingGroupID;

    @Column(nullable = false)
    private int PrimaryContactPersonID;

    @Column(nullable = false)
    private int AlternateContactPersonID;

    @Column(nullable = false)
    private int DeliveryMethodID;

    @Column(nullable = false)
    private int DeliveryCityID;

    @Column(nullable = false)
    private int PostalCityID;

    @Column(nullable = false)
    private double CreditLimit;

    @Column(nullable = false)
    private String AccountOpenedDate;

    @Column(nullable = false)
    private double StandardDiscountPercentage;

    @Column(nullable = false)
    private int IsStatementSent;

    @Column(nullable = false)
    private int IsOnCreditHold;

    @Column(nullable = false)
    private int PaymentDays;

    @Column(nullable = false)
    private String PhoneNumber;

    @Column(nullable = false)
    private String FaxNumber;

    @Column(nullable = false)
    private String DeliveryRun;

    @Column(nullable = false)
    private String RunPosition;

    @Column(nullable = false)
    private String WebsiteURL;

    @Column(nullable = false)
    private String DeliveryAddressLine1;

    @Column(nullable = false)
    private String DeliveryAddressLine2;

    @Column(nullable = false)
    private String DeliveryPostalCode;

    @Column(nullable = false)
    private String PostalAddressLine1;

    @Column(nullable = false)
    private String PostalAddressLine2;

    @Column(nullable = false)
    private String PostalPostalCode;

    @Column(nullable = false)
    private String LastEditedBy;

    @Column(nullable = false)
    private String ValidFrom;

    @Column(nullable = false)
    private String ValidTo;

    @OneToMany()
    @JoinColumn(name = "CustomerID")
    private List<Order> orders;

    public int getCustomerID() {
        return CustomerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public int getBillToCustomerID() {
        return BillToCustomerID;
    }
}

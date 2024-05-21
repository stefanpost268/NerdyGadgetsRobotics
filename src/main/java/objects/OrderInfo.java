package objects;

import java.util.ArrayList;

public class OrderInfo {
    private int OrderID;
    private String ExpectedDeliveryDate;
    private String Status;
    private int PhoneNumber;
    private String CustomerName;
    private String DeliveryAdress;
    private String Comments;
    private String DeliveryInstructions;
    private String InternalComments;
    private ArrayList<String> Products;

    public String getDeliveryAdress() {
        return DeliveryAdress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        DeliveryAdress = deliveryAdress;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getExpectedDeliveryDate() {
        return ExpectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        ExpectedDeliveryDate = expectedDeliveryDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }



    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getDeliveryInstructions() {
        return DeliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        DeliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return InternalComments;
    }

    public void setInternalComments(String internalComments) {
        InternalComments = internalComments;
    }

    public ArrayList<String> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<String> products) {
        Products = products;
    }

    @Override
    public String toString() {
        return
                OrderID + '\'' +
                        ExpectedDeliveryDate +
                        Status + '\'' +
                        PhoneNumber +
                        CustomerName + '\'' +
                        DeliveryAdress + '\'' +
                        Comments + '\'' +
                        DeliveryInstructions + '\'' +
                        InternalComments + '\'' +
                        Products;
    }
}


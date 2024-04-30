package models;

public class Order extends BaseModel<Order> {
    public Integer OrderID;
    public Integer OrderDate;
    public Integer CustomerID;
    public Integer ContactPersonID;
    public Integer SalespersonPersonID;
    public Integer PickedByPersonID;

    @Override
    public String[] fillable() {
        return new String[] {
            "OrderID",
            "OrderDate",
            "CustomerID",
            "ContactPersonID",
            "SalespersonPersonID",
            "PickedByPersonID",
        };
    }

    @Override
    public String getTableName() {
        return "orders";
    }

    @Override
    public Order createInstance() {
        return new Order();
    }
}

package models;

import jakarta.persistence.*;

@Entity
@Table(name = "stockitemholdings")
public class StockItemHolding {

    @Id
    private Integer StockItemID;

    @Column()
    private Integer QuantityOnHand;

    public Integer getQuantityOnHand() {
        return QuantityOnHand;
    }
}

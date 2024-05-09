package repositories;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import models.Order;
import org.springframework.stereotype.Repository;

public class OrderInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertOrder(Order order) {
        entityManager.createNativeQuery("INSERT INTO orders " +
                        "(CustomerID, SalespersonPersonID, PickedByPersonID, ContactPersonID, OrderDate, " +
                        "ExpectedDeliveryDate, IsUnderSupplyBackordered, Comments, DeliveryInstructions, " +
                        "InternalComments, LastEditedBy, LastEditedWhen)" +
                        "VALUES (?, ?, ?, ?, CURDATE(), CURDATE(), FALSE, ?, ?, ?, ?, CURDATE())")
                .setParameter(1, order.getCustomer().getCustomerID())
                .setParameter(2, order.getSalesperson().getPersonID())
                .setParameter(3, order.getPickedByPerson().getPersonID())
                .setParameter(4, order.getContactPerson().getPersonID())
                .setParameter(5, order.getComments())
                .setParameter(6, order.getDeliveryInstructions())
                .setParameter(7, order.getInternalComments())
                .setParameter(8, order.getLastEditedBy().getPersonID())
                .executeUpdate();
    }
}

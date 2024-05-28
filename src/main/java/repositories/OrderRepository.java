package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import models.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>
{
    @Query("SELECT o FROM Order o WHERE o.Status != 'Done' ORDER BY CASE WHEN o.Status = 'InProgress' THEN 0 WHEN o.Status = 'Open' THEN 2 ELSE 1 END, o.OrderDate")
    Page<Order> findUnfinishedOrders(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.Status != 'Done'")
    Page<Order> findFinishedOrders(Pageable pageable);

    @Query("SELECT o FROM Order o ORDER BY o.OrderDate DESC")
    Page<Order> findAllByDesc(Pageable pageable);
}

package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import models.Order;
import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Integer>
{
    @Query("SELECT o FROM Order o WHERE o.Status != 'Done'")
    Page<Order> findUnfinishedOrders(Pageable pageable);

    @Query("SELECT o FROM Order o ORDER BY o.OrderDate DESC")
    List<Order> findAllByDesc();
}

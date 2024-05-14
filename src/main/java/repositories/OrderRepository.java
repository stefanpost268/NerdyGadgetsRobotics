package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>
{
    @Query("SELECT o FROM Order o ORDER BY o.OrderDate DESC")
    List<Order> findAllByDesc();
}

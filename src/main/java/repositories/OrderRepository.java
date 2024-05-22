package repositories;

import org.springframework.data.repository.CrudRepository;
import models.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>
{

}

package repositories;

import models.OrderLine;
import org.springframework.data.repository.CrudRepository;

public interface OrderLinesRepository extends CrudRepository<OrderLine, Integer> {

}
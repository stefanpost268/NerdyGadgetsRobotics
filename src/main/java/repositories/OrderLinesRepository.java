package repositories;

import models.OrderLines;
import org.springframework.data.repository.CrudRepository;

public interface OrderLinesRepository extends CrudRepository<OrderLines, Integer> {

}
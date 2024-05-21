package repositories;

import models.StockItem;
import org.springframework.data.repository.CrudRepository;

public interface StockItemRepository extends CrudRepository<StockItem, Integer>
{
    Iterable<StockItem> findByStockItemNameContaining(String stockItemName);
}
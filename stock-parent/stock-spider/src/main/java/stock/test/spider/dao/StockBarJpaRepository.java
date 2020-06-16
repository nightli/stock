package stock.test.spider.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stock.test.spider.entity.StockBar;

@Repository
public interface StockBarJpaRepository extends CrudRepository<StockBar, Long> {
}

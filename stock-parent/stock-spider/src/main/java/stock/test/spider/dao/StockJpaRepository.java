package stock.test.spider.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stock.test.spider.entity.StockIndustry;

@Repository
public interface StockJpaRepository  extends CrudRepository<StockIndustry, Long> {
}

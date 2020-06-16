package stock.test.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.test.spider.entity.StockCode;

@Repository
public interface StockCodeJpaRepository extends JpaRepository<StockCode, Long> {
}

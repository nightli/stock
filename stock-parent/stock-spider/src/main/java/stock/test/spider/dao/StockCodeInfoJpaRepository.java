package stock.test.spider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.test.spider.entity.StockCode;
import stock.test.spider.entity.StockCodeInfo;

@Repository
public interface StockCodeInfoJpaRepository extends JpaRepository<StockCodeInfo, Long> {
}

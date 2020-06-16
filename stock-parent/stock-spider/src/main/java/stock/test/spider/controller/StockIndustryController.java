package stock.test.spider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import stock.test.spider.dao.StockJpaRepository;
import stock.test.spider.entity.StockIndustry;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author night
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/spider/stock-industry")
public class StockIndustryController {
    @Autowired
    StockJpaRepository stockJpaRepository;

    @GetMapping("/hh")
    public void getHH(){
        StockIndustry stockIndustry = new StockIndustry();
        stockIndustry.setCode("fdff");
        stockIndustry.setName("333");
        stockIndustry.setDeleted(false);
        stockJpaRepository.save(stockIndustry);
    }
}


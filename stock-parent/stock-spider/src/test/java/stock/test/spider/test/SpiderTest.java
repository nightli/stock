package stock.test.spider.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import stock.test.common.utils.StockSpiderUtil;
import stock.test.spider.dao.StockBarJpaRepository;
import stock.test.spider.dao.StockCodeInfoJpaRepository;
import stock.test.spider.dao.StockCodeJpaRepository;
import stock.test.spider.dao.StockJpaRepository;
import stock.test.spider.entity.StockBar;
import stock.test.spider.entity.StockCode;
import stock.test.spider.entity.StockCodeInfo;
import stock.test.spider.entity.StockIndustry;
import stock.test.spider.service.EastMoneySpider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "stock.test.spider.dao.StockJpaRepository")
public class SpiderTest {
    @Autowired
    StockJpaRepository stockJpaRepository;
    @Autowired
    StockCodeJpaRepository stockCodeJpaRepository;
    @Autowired
    StockBarJpaRepository stockBarJpaRepository;
    @Autowired
    StockCodeInfoJpaRepository stockCodeInfoJpaRepository;


    private static Logger log= LoggerFactory.getLogger(SpiderTest.class);
    @Test
    public void testGet() throws IOException {
        EastMoneySpider eastMoneySpider = new EastMoneySpider();
        for (int j = 1; j <= 4; j++) {
            List<StockIndustry> list = (ArrayList)eastMoneySpider.getIndustryList(j,20);
            for (int i = 0; i < list.size(); i++) {
                stockJpaRepository.save(list.get(i));
                log.info("股票列表：{}",list.get(i));
            }
        }


    }

    @Test
    public void testGetStockList() throws IOException {
        EastMoneySpider eastMoneySpider = new EastMoneySpider();
        List<StockIndustry> industryList = (List<StockIndustry>) stockJpaRepository.findAll();
        for (int i = 0; i < industryList.size(); i++) {
            List<StockCodeInfo> stockList = (ArrayList) eastMoneySpider.getStockList(industryList.get(i).getCode());
            stockCodeInfoJpaRepository.saveAll(stockList);
            log.info("股票列表：{}",stockList);
        }
    }

    @Test
    public void testPopularRank() throws IOException {
        String url = "http://guba.eastmoney.com/interface/GetData.aspx";
        List<StockCode> industryList = (List<StockCode>) stockCodeJpaRepository.findAll();
        for (int i = 0; i < industryList.size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("param","code="+industryList.get(i).getCode());
            map.put("path","webarticlelist/api/guba/gubainfo");
            map.put("env","2");
            Document infoDoc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36").referrer("http://guba.eastmoney.com/list,000860.html")
                    .timeout(100000)
                    //.cookie("xq_a_token", xueQiuToken)
                    .data(map)
                    .ignoreContentType(true)
                    .post();
            String result = infoDoc.text();
            result = result.substring(result.indexOf("{"),result.indexOf("}")+1);
            JSONObject jsonObject = JSONObject.parseObject(result);
            StockBar stockBar = new StockBar();
            stockBar.setBarRank(jsonObject.getString("bar_rank"))
                    .setCode(industryList.get(i).getCode())
                    .setPopularRank(jsonObject.getString("popular_rank"))
                    .setStockbarFansCount(jsonObject.getLong("stockbar_fans_count"));
            stockBarJpaRepository.save(stockBar);
        }
    }


    @Test
    public void testStockInfo() throws IOException {
        List<StockIndustry> stockCodeList = (List<StockIndustry>) stockJpaRepository.findAll();
        for (int i = 0; i < stockCodeList.size(); i++) {
            String url = "http://push2.eastmoney.com/api/qt/clist/get?ut=bd1d9ddb04089700cf9c27f6f7426281&pi=0&pz=500&po=1&fid=f62&fs=b:"+stockCodeList.get(i).getCode()+"&&fields=f1,f2,f12,f13,f14,f62,f152,f128,f3,f62,f184,f66,f69,f72,f75,f78,f81,f84,f87&cb=jQuery112405159178004039402_1591624182533&_=1591624182567";
            //String url = "http://push2.eastmoney.com/api/qt/stock/get?invt=2&fltt=2&ut=a79f54e3d4c8d44e494efb8f748db291&secid=0."+stockCodeList.get(i).getCode()+"&fields=f57,f58,f43,f78,f169,f170&cb=jQuery18308866161706871811_1591451135381&_=1591451138055";
            String result = StockSpiderUtil.getHttpResult(url);
            result = result.substring(result.indexOf("(")+1,result.indexOf(")"));
            JSONObject jsonObject = JSONObject.parseObject(result).getJSONObject("data").getJSONObject("diff");
            int total = JSONObject.parseObject(result).getJSONObject("data").getInteger("total");
            for (int j = 0; j < total; j++) {
                JSONObject stock = jsonObject.getJSONObject(j+"");
                StockCodeInfo stockCodeInfo = new StockCodeInfo();
                stockCodeInfo.setCode(stock.getString("f12"))
                        .setName(stock.getString("f14"))
                        .setPrice(stock.getBigDecimal("f2"))
                        .setTrend(stock.getBigDecimal("f3"));
                stockCodeInfoJpaRepository.save(stockCodeInfo);
            }

        }

    }
}

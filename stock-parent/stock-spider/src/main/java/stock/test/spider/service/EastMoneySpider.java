package stock.test.spider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import stock.test.common.utils.StockSpiderUtil;
import stock.test.spider.dao.StockJpaRepository;
import stock.test.spider.entity.StockCode;
import stock.test.spider.entity.StockCodeInfo;
import stock.test.spider.entity.StockIndustry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EastMoneySpider implements StockSpider{

    @Value("${eastmoney.token}")
    private String token;

    @Value("${spider.useagent}")
    private String useAgent;

    @Value("${spider.timeout}")
    private int timeout;

    @Override
    public JSONArray getStockPrice(String[] stockCode) throws Exception {
        return null;
    }

    @Override
    public JSONObject getStockInfo(String code) throws Exception {

        return null;
    }

    @Override
    public JSONArray getHistoryDividendRate(String code) throws Exception {
        return null;
    }

    @Override
    public JSONArray getHistoryROE(String code) throws Exception {
        return null;
    }

    @Override
    public List<String> getAllStockCode() throws Exception {
        return null;
    }

    @Override
    public JSONObject getDy(int page) throws Exception {
        return null;
    }

    @Override
    public List<String> getStockCodeBySH() throws Exception {
        return null;
    }

    @Override
    public List<String> getStockCodeBySZ() throws Exception {
        return null;
    }
    @Autowired
    StockJpaRepository stockJpaRepository;

    public List<?> getIndustryList(int pn,int pz) throws IOException {
        String url = "http://77.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112405463724894585738_1591058356677&pn="+pn+"&pz="+pz+"&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:90+t:2+f:!50&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152,f124,f107,f104,f105,f140,f141,f207,f222&_=1591058356678";
        String result = StockSpiderUtil.getHttpResult(url);
        result = result.substring(result.indexOf("(")+1,result.indexOf(")"));
        //String str = new String(decoder.decode(JSON.parseObject(result).get("result").toString()), "UTF-8");
        String  str  = JSON.parseObject(result).getObject("data",JSONObject.class).get("diff").toString();//.get("answers").toString();//get("answers");
        //String title = jsonObject.get("title").toString();
        JSONArray jsonArray = JSON.parseArray(str);
        //jsonArray.stream().map()
        /*List arrayList = jsonArray.stream().
                map((a)-> JSONObject.parseObject(a.toString(),Map.class)).
                map(temp-> {return (HashMap<String,String>)temp.get("fl2");}).
                collect(Collectors.toList());*/
        List<StockIndustry> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Map<String,String> map = JSONObject.parseObject(jsonArray.get(i).toString(),Map.class);
            StockIndustry stockIndustry = new StockIndustry();
            stockIndustry.setName(map.get("f14")).setCode(map.get("f12"));
            list.add(stockIndustry);
            //stockJpaRepository.save(stockIndustry);
        }

      //jsonArray.stream().map(temp->JSONObject.parseObject(temp.toString(),Map.class)).map(temp->new StockIndustry().setCode((Map)temp.get("f12")).setName("f20")).collect(Collectors.toList());

        return list;

    }

    public List<?> getStockList(String industryCode) throws IOException {
        List<?> arraylist = new ArrayList<>();
        //获取行业的所有股票url
        String url = "http://push2.eastmoney.com/api/qt/clist/get?pi=0&pz=600&po=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fs=b:"+industryCode+"&fid=f3&fields=f1,f2,f3,f12,f13,f14,f152&cb=jQuery112404035056137123869_1591402665502&_=1591402665556";
        String result = StockSpiderUtil.getHttpResult(url);

        result = result.substring(result.indexOf("(")+1,result.indexOf(")"));
        //String str = new String(decoder.decode(JSON.parseObject(result).get("result").toString()), "UTF-8");
        JSONObject jsonObject  = JSON.parseObject(result).getObject("data",JSONObject.class);
        JSONObject data  = jsonObject.getJSONObject("diff");//.get("answers").toString();//get("answers");
        int total = Integer.parseInt(jsonObject.get("total").toString());

       /*
         板块的所有股票
         List<StockCode> list = new ArrayList<>(total);
        for (int i = 0; i < total; i++){
            StockCode stockCode = new StockCode();
            JSONObject var = data.getJSONObject(i+"");
            stockCode.setCode(var.getString("f12")).setName(var.getString("f14")).setIndustryCode(industryCode);
            list.add(stockCode);
        }*/
        List<StockCodeInfo> list = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            StockCodeInfo stockCodeInfo = new StockCodeInfo();
            JSONObject var = data.getJSONObject(i + "");
            stockCodeInfo.setCode(var.getString("f12")).setName(var.getString("f14")).setPrice(var.getBigDecimal("f2")).setTrend(var.getBigDecimal("f3"));
            list.add(stockCodeInfo);
        }
        return list;

    }
}

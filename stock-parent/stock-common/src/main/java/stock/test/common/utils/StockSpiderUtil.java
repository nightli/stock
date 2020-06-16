package stock.test.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class StockSpiderUtil {
    public static String getHttpResult(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(get);
        //String url = "http://77.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112405463724894585738_1591058356677&pn=1&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:90+t:2+f:!50&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152,f124,f107,f104,f105,f140,f141,f207,f222&_=1591058356678";
        String result = EntityUtils.toString(closeableHttpResponse.getEntity());
        return result;
    }

    public static List<?> getListFromHtml(String result){
        result = result.substring(result.indexOf("(")+1,result.indexOf(")"));
        //String str = new String(decoder.decode(JSON.parseObject(result).get("result").toString()), "UTF-8");
        JSONObject jsonObject  = JSON.parseObject(result).getObject("data",JSONObject.class);
        String data  = jsonObject.get("diff").toString();//.get("answers").toString();//get("answers");
        String total = jsonObject.get("total").toString();
        //String title = jsonObject.get("title").toString();
        //JSONArray jsonArray = JSON.parseArray(str);

        //return jsonArray;
        return null;
    }
}

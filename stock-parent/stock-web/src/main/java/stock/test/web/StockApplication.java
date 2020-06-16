package stock.test.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"stock.test.web"})
public class StockApplication {
    public static void main(String[] args){
        SpringApplication.run(StockApplication.class, args);
    }

}

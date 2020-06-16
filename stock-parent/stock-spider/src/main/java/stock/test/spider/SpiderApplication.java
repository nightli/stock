package stock.test.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityListeners;

@SpringBootApplication
/*@ComponentScan(basePackages={"stock.test.spider"})
@EnableCaching*/
@EnableJpaAuditing

public class SpiderApplication {
    public static void main(String[] args){
        SpringApplication.run(SpiderApplication.class, args);
    }
}

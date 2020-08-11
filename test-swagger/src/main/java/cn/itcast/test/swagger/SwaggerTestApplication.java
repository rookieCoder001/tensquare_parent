package cn.itcast.test.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 17:13
 **/
@SpringBootApplication
public class SwaggerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerTestApplication.class,args);
    }
}

package br.com.hyzed.hyzedapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "hyzed-api", version = "1.0", description = "Ecommerce API for hyzedwear"))
public class HyzedApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyzedApiApplication.class, args);
    }

}

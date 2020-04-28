package nl.quintor.dpanis.productapi;

import nl.quintor.dpanis.productapi.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class ProductApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }

}

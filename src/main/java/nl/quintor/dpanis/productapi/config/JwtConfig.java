package nl.quintor.dpanis.productapi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Validated
@Getter
@Setter
@NoArgsConstructor
public class JwtConfig {

    private String signingKey;
    private String tokenPrefix;
    private String tokenHeader;
    private String authoritiesKey;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration tokenDurationSeconds;

}

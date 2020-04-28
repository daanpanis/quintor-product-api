package nl.quintor.dpanis.productapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "jwt")
@Validated
@Getter
@Setter
public class JwtConfig {

    private String signingKey;
    private String tokenPrefix;
    private String tokenHeader;
    private String authoritiesKey;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration tokenDurationSeconds;

}

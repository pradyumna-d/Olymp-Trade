package killhouser.trading.config.authentication;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import killhouser.trading.models.auth.Cors;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties("authentication-config")
public class AuthenticationConfig {

  @NotNull @NotEmpty private String secretKey;

  @NotNull @NotEmpty private String[] whitelistedApis;

  private String[] whitelistedPhoneNumbers;

  @NotNull private Cors cors;

  @NotNull private Long authTokenExpiryTimeInMilliSeconds;

  @NotNull private Long refreshTokenExpiryTimeInSeconds;
}

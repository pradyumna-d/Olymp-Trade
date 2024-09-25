package killhouser.trading.config.command;

import killhouser.trading.config.olymptrade.OlympTradeConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
public class CommandConfig {

  private static final String PREFIX = "http-client-config.";

  @Bean(value = ClientType.OLYMP_TRADE_CLIENT_NAME)
  @ConfigurationProperties(prefix = PREFIX + ClientType.OLYMP_TRADE_CLIENT_NAME)
  public OlympTradeConfig olympTradeHttpClientConfiguration() {
    return new OlympTradeConfig();
  }
}

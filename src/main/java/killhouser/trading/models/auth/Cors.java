package killhouser.trading.models.auth;

import lombok.Data;

import java.util.List;

@Data
public class Cors {

  private List<String> allowedOrigins;

  private List<String> allowedMethods;

  private List<String> allowedHeaders;
}

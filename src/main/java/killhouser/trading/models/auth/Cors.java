package killhouser.trading.models.auth;

import java.util.List;
import lombok.Data;

@Data
public class Cors {

  private List<String> allowedOrigins;

  private List<String> allowedMethods;

  private List<String> allowedHeaders;
}

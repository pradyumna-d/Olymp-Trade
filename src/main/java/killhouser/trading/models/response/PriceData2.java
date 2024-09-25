package killhouser.trading.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceData2 {

  @JsonProperty("ts")
  private BigDecimal ts;

  @JsonProperty("mid")
  private Double mid;
}

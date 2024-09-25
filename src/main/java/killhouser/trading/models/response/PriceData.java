package killhouser.trading.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceData {

  @JsonProperty("ts")
  private Double epochTime;

  @JsonProperty("mid")
  private Double price;
}

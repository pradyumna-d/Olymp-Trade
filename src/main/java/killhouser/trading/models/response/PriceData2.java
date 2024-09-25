package killhouser.trading.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

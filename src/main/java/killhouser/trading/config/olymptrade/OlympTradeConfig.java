package killhouser.trading.config.olymptrade;

import com.irex.http.config.HttpClientConfiguration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OlympTradeConfig extends HttpClientConfiguration {}

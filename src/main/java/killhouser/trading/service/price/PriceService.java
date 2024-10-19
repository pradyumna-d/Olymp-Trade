package killhouser.trading.service.price;

import java.io.IOException;
import killhouser.trading.models.enums.Index;
import killhouser.trading.models.request.FetchTradingPriceRequest;
import killhouser.trading.models.response.FetchTradingPriceResponse;
import org.springframework.stereotype.Service;

@Service
public interface PriceService {
  FetchTradingPriceResponse fetchPrice(FetchTradingPriceRequest request);

  FetchTradingPriceResponse fetchTradingPrice(Long from, Long to);

  void addPriceData(FetchTradingPriceRequest request);

  FetchTradingPriceResponse fetchPriceV2(Long from, Long to, Index index)
      throws IOException, InterruptedException;
}

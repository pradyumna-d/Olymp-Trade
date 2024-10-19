package killhouser.trading.controller;

import java.io.IOException;
import killhouser.trading.models.enums.Index;
import killhouser.trading.models.request.FetchTradingPriceRequest;
import killhouser.trading.models.response.FetchTradingPriceResponse;
import killhouser.trading.service.price.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/price", produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

  @Autowired PriceService priceService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public FetchTradingPriceResponse fetchPrice(@RequestBody FetchTradingPriceRequest request) {
    return priceService.fetchPrice(request);
  }

  @GetMapping
  public FetchTradingPriceResponse getPrice(
      @RequestParam(value = "from") Long from, @RequestParam("to") Long to) {
    return priceService.fetchTradingPrice(from, to);
  }

  @GetMapping(path = "/v2")
  public FetchTradingPriceResponse fetchPrice(
      @RequestParam("from") Long from,
      @RequestParam("to") Long to,
      @RequestParam("index") Index index)
      throws IOException, InterruptedException {
    return priceService.fetchPriceV2(from, to, index);
  }

  @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addPriceData(@RequestBody FetchTradingPriceRequest request) {
    priceService.addPriceData(request);
  }
}

package killhouser.trading.controller;

import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import killhouser.trading.models.request.FetchTradingPriceRequest;
import killhouser.trading.models.response.FetchTradingPriceResponse;
import killhouser.trading.service.price.PriceService;
import killhouser.trading.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping(path = "/price", produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

    @Autowired PriceService priceService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public FetchTradingPriceResponse fetchPrice(@RequestBody FetchTradingPriceRequest request){
        return priceService.fetchPrice(request);
    }

    @GetMapping
    public FetchTradingPriceResponse getPrice(@RequestParam(value = "from") Long from, @RequestParam("to") Long to){
        return priceService.fetchTradingPrice(from, to);
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addPriceData(@RequestBody FetchTradingPriceRequest request){
        priceService.addPriceData(request);
    }

}

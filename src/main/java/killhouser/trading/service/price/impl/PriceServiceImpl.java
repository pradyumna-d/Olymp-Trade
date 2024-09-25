package killhouser.trading.service.price.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import killhouser.trading.command.OlympTradeCommand;
import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import killhouser.trading.models.request.FetchTradingPriceRequest;
import killhouser.trading.models.response.FetchTradingPriceResponse;
import killhouser.trading.models.response.PriceData2;
import killhouser.trading.service.price.PriceService;
import killhouser.trading.storage.mysql.CommodityIndexEntityService;
import killhouser.trading.storage.mysql.entity.commodity.CommodityIndexEntity;
import killhouser.trading.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PriceServiceImpl implements PriceService {

  @Autowired OlympTradeCommand olympTradeCommand;

  @Autowired CommodityIndexEntityService commodityIndexEntityService;

  @Override
  public FetchTradingPriceResponse fetchPrice(FetchTradingPriceRequest request) {
    return olympTradeCommand.getPriceHistory(request);
  }

  @Override
  public FetchTradingPriceResponse fetchTradingPrice(Long from, Long to) {
    try {
      // Path to your bash script
      String scriptPath = "/Users/pradyumna-d/index1-data.sh";

      // Create a ProcessBuilder to run the script with the provided parameters
      ProcessBuilder processBuilder =
          new ProcessBuilder(scriptPath, from.toString(), to.toString());
      processBuilder.redirectErrorStream(true);

      // Start the process
      Process process = processBuilder.start();

      // Read output from the script
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      StringBuilder output = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }

      // Wait for the process to finish and capture the exit code
      int exitCode = process.waitFor();
      if (exitCode != 0) {
        // Return an error response if the script execution fails
        throw BaseException.create(ResponseCode.UNKNOWN_EXCEPTION, "internal server error");
      }
      // Get the output as a string
      String rawOutput = output.toString();

      // Find and clean malformed JSON object
      String cleanedOutput = cleanMalformedJson(rawOutput);

      log.info("Clean JSON is :: {}", cleanedOutput);

      // Deserialize the clean JSON output into FetchTradingPriceResponse
      return JacksonUtil.deserialize(cleanedOutput, FetchTradingPriceResponse.class);

    } catch (Exception e) {
      // Handle any exceptions that occur during script execution or deserialization
      throw BaseException.create(ResponseCode.JSON_ERROR, "unable to deserialize");
    }
  }

  @Override
  public void addPriceData(FetchTradingPriceRequest request) {
    FetchTradingPriceResponse response = fetchTradingPrice(request.getFrom(), request.getTo());
    List<CommodityIndexEntity> commodityIndexEntities = new ArrayList<>();
    for (PriceData2 priceData : response.getData()) {
      CommodityIndexEntity commodityIndex =
          CommodityIndexEntity.builder()
              .price(priceData.getMid())
              .epochTimeStamp(priceData.getTs())
              .build();
      commodityIndexEntities.add(commodityIndex);
    }
    commodityIndexEntityService.saveAll(commodityIndexEntities);
  }

  private String cleanMalformedJson(String input) {

    int startIndex = input.indexOf("{");
    String jsonData = input.substring(startIndex);
    // Regular expression to match only valid objects: {"ts":<number>,"mid":<number>}
    String pattern = "\\{\"ts\":\\d+\\.\\d+,\"mid\":\\d+\\.\\d+\\}";
    Pattern regex = Pattern.compile(pattern);
    Matcher matcher = regex.matcher(jsonData);

    // Collect all valid objects
    StringBuilder validJson = new StringBuilder();
    validJson.append("{\"data\":[");

    boolean first = true;
    while (matcher.find()) {
      if (!first) {
        validJson.append(",");
      }
      validJson.append(matcher.group());
      first = false;
    }

    validJson.append("]}");

    return validJson.toString();
  }
}

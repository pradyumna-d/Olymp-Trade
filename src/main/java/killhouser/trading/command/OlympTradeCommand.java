package killhouser.trading.command;

import static com.irex.http.ApacheHttpClient.fetchExecutor;
import static killhouser.trading.common.constants.Constants.COOKIE;
import static killhouser.trading.common.constants.Constants.X_CID_APP;
import static killhouser.trading.config.command.ClientType.OLYMP_TRADE_CLIENT_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irex.http.ApacheHttpClient;
import com.irex.http.config.HttpAppConfiguration;
import com.irex.http.model.HttpHeader;
import com.irex.http.model.HttpMethod;
import com.irex.http.model.HttpRequest;
import com.irex.http.model.payload.JsonRequestBody;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import killhouser.trading.config.olymptrade.OlympTradeConfig;
import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import killhouser.trading.models.enums.Index;
import killhouser.trading.models.request.FetchTradingPriceRequest;
import killhouser.trading.models.response.FetchTradingPriceResponse;
import killhouser.trading.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OlympTradeCommand {
  private final CloseableHttpClient httpClient;
  private final OlympTradeConfig olympTradeConfig;
  private final HttpAppConfiguration httpAppConfiguration;

  public OlympTradeCommand(
      @Qualifier(OLYMP_TRADE_CLIENT_NAME) OlympTradeConfig olympTradeConfig,
      HttpAppConfiguration httpAppConfiguration) {
    this.httpClient = ApacheHttpClient.createClient(olympTradeConfig);
    this.olympTradeConfig = olympTradeConfig;
    this.httpAppConfiguration = httpAppConfiguration;
  }

  public FetchTradingPriceResponse getPriceHistory(FetchTradingPriceRequest request) {
    try {
      Set<HttpHeader> headers = new HashSet<>();
      setHeaders(headers);
      HttpRequest httpRequest =
          HttpRequest.builder()
              .httpClient(this.httpClient)
              .httpAppConfiguration(httpAppConfiguration)
              .httpMethod(HttpMethod.POST)
              .httpHeaders(headers)
              .endpoint(olympTradeConfig.getEndpoint())
              .httpRequestBody(JsonRequestBody.builder().data(request).build())
              .path("/quote-history/ftt/v1")
              .objectMapper(JacksonUtil.getMapper())
              .build();
      return fetchExecutor(httpRequest).execute(FetchTradingPriceResponse.class, null);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "Error while calling olymp trade price history api :: message :: %s ",
              e.getMessage());
      throw BaseException.create(
          ResponseCode.OLYMP_TRADE_ERROR, "can't call olymp trade", logMessage, e);
    }
  }

  public FetchTradingPriceResponse fetchPrice(Index index, Long from, Long to)
      throws IOException, InterruptedException {

    // Dynamically build the request body using the provided index, from, and to parameters
    String requestBody =
        "{\n\t\"pair\": \""
            + index.getValue()
            + "\",\n\t\"from\": "
            + from
            + ",\n\t\"to\": "
            + to
            + "\n}";

    // create the http request
    java.net.http.HttpRequest request =
        java.net.http.HttpRequest.newBuilder()
            .uri(URI.create("https://gw.olymptrade.com/api/quote-history/ftt/v1"))
            // figure out a way to extract this cookie from olymp trade
            .header(
                "cookie",
                "guest_id=1000191737482358244374612089286511725261435360177847340202659798; hubspotutk=4207371be8ac24e214dbb1755c9fb483; __hssrc=1; _fbp=fb.1.1725261436930.314084344109801587; _gcl_au=1.1.432244440.1725261443; _scid=f87bf1c8-305c-4ecb-973b-0445e2bd8e99; _tt_enable_cookie=1; _ttp=SDIzRdIgRYLEMaf7CsIubOcpt6x; __exponea_etc__=1d7e4286-c54c-481e-990d-ab43faead5b6; uhdwidv2=hi_0gqgkgRr5XC9T4qPyCPegz1Rm4gTxP1z; _ym_uid=1725430286587927986; _ym_d=1725430286; _ga=GA1.1.1326135625.1725261443; _ga_S8V6XQCRYT=GS1.1.1725442578.3.1.1725442612.0.0.0; _ga_4ZJWVC8QN4=GS1.1.1725442578.3.1.1725442612.0.0.0; _ga_CEKM6CNCVP=GS1.1.1725442578.1.1.1725442612.0.0.0; checked=1; enterdate=2024-10-19+09%3A39%3A52; lang=en_US; __cflb=02DiuGSURUTCLDAS4xXvR9mGzL7JM9p8qQYXNSy4TzWcU; _cfuvid=ZoqkOIKMGkmEeBbAdOdC5aUqwjSx_7zcgJ1uW_hugoU-1729319992775-0.0.1.1-604800000; __hstc=95761603.4207371be8ac24e214dbb1755c9fb483.1725261436517.1727253998348.1729319993592.14; ecp=d6a9eada693902be02c9fa028d4313d5; ece=d6a9eada693902be02c9fa028d4313d5; ecc=d6a9eada693902be02c9fa028d4313d5; otrhIIBzICu=d6a9eada693902be02c9fa028d4313d5; _ScCbts=%5B%5D; _clck=11mudk7%7C2%7Cfq5%7C0%7C1706; __exponea_time2__=-0.18615341186523438; _sctr=1%7C1729276200000; __hssc=95761603.4.1729319993592; access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3Mjk0OTM0MTYsImlhdCI6MTcyOTMyMDYxNiwiaWQiOjQ4NzcyMzE3NSwibmJmIjoxNzI5MzIwNjE2LCJyZXFfY3R4X2hhc2giOiI5M2FlM2IzMjQ1ZjQ0ODIyMGJhYzY4YjhkZTlkMDUwNCIsInR5cGUiOiJiZWFyZXIiLCJ1c2VyX2lkIjoxMTk5MDcwNzV9.ioxVdEt7jThv5Z_k5t9lnJvHyOfgMMc7K9BMgTZib9WPhStypMzSx6MG3mTdcHOWsgXAnokJCARaswtP5PWawiVgdo9NoS5Enr3MU63Hes3j2k3_gkU-rFAVejdCWgblhNuFLB3FiK9EeL518FEgva29NmU8XGRD96KEFI6oyAx_qhYAkuEEvWa2ejQy5Grk-vNWlCQRAbn3LMs13Qg_7v5RS_MGMVWYyv3vuuxrp1x60Es9IU6HE364MkvcKbcHmJfV-Gsurzc2MEING-kWxyGXTX-BRDPf9JA2l0f8sx0B65DEj0xamIXFmRdz4aQYuMZKrMUu2VM4djzm0ogpMmwRtcom8ZCBUHGokcUmMsIRgLsYb-gwTImvV3KhSiAobQgTBXzgEEY80fmMUACQjaCeRVmy2u93vEQ4noigvEKe3cHQ5RaeaSdUhDBEXCqF919p6QCuc9MJfn-BC8BEEkmKOpQveYa32N_Kr0RzedhRvziUefGi3sz4NRAy_qg8_xeS1UP4tXt1LT9VeQwdu9QTm_dy6xOBFLM4XsXPbBVw84Y06-SLyeesrV7aYAzpCmgQ21Hhk57AjlosARjxeXbgc-gv4qxMI874IhH1nEHIZAFVrP-Jz6hGaqN711Gu6AE8OIRUDU3MRiCrdx7wkBCHqRw9vg85xgFYJyFlmOQ; _rdt_uuid=1725261442763.80adabcc-c006-4340-bb3b-a326f8293a79; _rdt_em=0000000000000000000000000000000000000000000000000000000000000001; _uetsid=f74075408de411ef8d9b1b167221d754; _uetvid=660f688068fb11ef9f7f55bc2b640926; _scid_r=kgT4e_HIMFzEy407BEXivY6ZtPUmUgRwzz3__w; _clsk=1uhdkr2%7C1729320628778%7C3%7C1%7Ce.clarity.ms%2Fcollect; _ga_E2F15RBL82=GS1.1.1729320000.19.1.1729321028.0.0.0; _ga_SN8XZNJ2M7=GS1.1.1729320000.14.1.1729321028.60.0.0")
            .header("accept", "*/*")
            .header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
            .header("content-type", "application/json")
            .header("x-cid-app", "web@OlympTrade@2024.3.24066@24066")
            .header("x-cid-ver", "1")
            .method("POST", java.net.http.HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    // execute the request and collect the response
    HttpResponse<String> response =
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    // Check if the response status code is 200 (OK)
    if (response.statusCode() == 200) {
      // Use ObjectMapper to parse the JSON response into the FetchTradingPriceResponse class
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(response.body(), FetchTradingPriceResponse.class);
    } else {
      // Handle the case when the response is not successful
      throw new IOException("Failed to fetch trading price: " + response.statusCode());
    }
  }

  private void setHeaders(Set<HttpHeader> headers) {
    headers.add(HttpHeader.builder().name("x-cid-app").value(X_CID_APP).build());
    headers.add(HttpHeader.builder().name("x-dic-ver").value("1").build());
    headers.add(HttpHeader.builder().name("cookie").value(COOKIE).build());
  }
}

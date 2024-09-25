package killhouser.trading.command;

import com.irex.http.ApacheHttpClient;
import com.irex.http.config.HttpAppConfiguration;
import com.irex.http.model.HttpHeader;
import com.irex.http.model.HttpMethod;
import com.irex.http.model.payload.JsonRequestBody;
import killhouser.trading.config.olymptrade.OlympTradeConfig;
import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import killhouser.trading.models.request.FetchTradingPriceRequest;
import killhouser.trading.models.response.FetchTradingPriceResponse;
import killhouser.trading.utils.JacksonUtil;
import killhouser.trading.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import com.irex.http.model.HttpRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.irex.http.ApacheHttpClient.fetchExecutor;
import static killhouser.trading.common.constants.Constants.COOKIE;
import static killhouser.trading.common.constants.Constants.X_CID_APP;
import static killhouser.trading.config.command.ClientType.OLYMP_TRADE_CLIENT_NAME;

@Slf4j
@Component
public class OlympTradeCommand {
    private final CloseableHttpClient httpClient;
    private final OlympTradeConfig olympTradeConfig;
    private final HttpAppConfiguration httpAppConfiguration;

    public OlympTradeCommand(@Qualifier(OLYMP_TRADE_CLIENT_NAME) OlympTradeConfig olympTradeConfig, HttpAppConfiguration httpAppConfiguration) {
        this.httpClient = ApacheHttpClient.createClient(olympTradeConfig);
        this.olympTradeConfig = olympTradeConfig;
        this.httpAppConfiguration = httpAppConfiguration;
    }

    public FetchTradingPriceResponse getPriceHistory(FetchTradingPriceRequest request){
        try {
            Set<HttpHeader> headers = new HashSet<>();
            setHeaders(headers);
            HttpRequest httpRequest = HttpRequest.builder()
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
        } catch (Exception e){
            String logMessage = String.format("Error while calling olymp trade price history api :: message :: %s ", e.getMessage());
            throw BaseException.create(ResponseCode.OLYMP_TRADE_ERROR, "can't call olymp trade", logMessage, e);
        }
    }

    private void setHeaders(Set<HttpHeader> headers){
        headers.add(HttpHeader.builder().name("x-cid-app").value(X_CID_APP).build());
        headers.add(HttpHeader.builder().name("x-dic-ver").value("1").build());
        headers.add(HttpHeader.builder().name("cookie").value(COOKIE).build());
    }
}

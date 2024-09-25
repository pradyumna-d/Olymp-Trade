package killhouser.trading.config.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import killhouser.trading.utils.JacksonUtil;
import killhouser.trading.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {

  @Primary
  @Bean(value = "objectMapper")
  public ObjectMapper configureObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.findAndRegisterModules();
    JacksonUtil.setup(objectMapper);
    return objectMapper;
  }

  @Bean(value = "jsonUtil")
  public JsonUtils configureJsonMapper(@Qualifier("objectMapper") final ObjectMapper mapper) {
    return new JsonUtils(mapper);
  }
}

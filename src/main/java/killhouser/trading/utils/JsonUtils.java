package killhouser.trading.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

  @Getter private final ObjectMapper mapper;

  public JsonUtils(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public <T> T deserialize(Object json, Class<T> clazz) {
    if (Objects.isNull(json)) {
      return null;
    }
    try {
      return mapper.convertValue(json, clazz);
    } catch (Exception e) {
      String logMessage =
          String.format("Error in deserializing json object with convertValue :: %s", json);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public <T> T deserialize(String json, Class<T> clazz) {
    try {
      if (StringUtils.isStringNullOrEmpty(json)) {
        return null;
      }
      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      String logMessage =
          String.format("Error in deserializing json object with readValue :: %s", json);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public <T> T deserialize(String json, TypeReference<T> clazz) {
    try {
      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      String logMessage = String.format("Error in deserializing json object :: %s", json);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public Map<String, Object> convertToMap(Object source) {

    if (Objects.isNull(source)) {
      return null;
    }
    try {
      return mapper.convertValue(source, new TypeReference<>() {});
    } catch (Exception e) {

      String logMessage = String.format("Error in converting object to map :: %s", source);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public String serialize(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (Exception e) {
      log.error("JsonUtils :: error while serializing object : {}", object);
      String logMessage = String.format("Error in serializing string object value :: %s", object);
      throw BaseException.create(ResponseCode.JSON_ERROR, "error in serialization", logMessage, e);
    }
  }

  public byte[] convertToBytes(Object object) {
    try {
      return mapper.writeValueAsBytes(object);
    } catch (IOException var4) {
      String logMessage =
          String.format(
              "JsonUtil :: convertToBytes : Error in deserializing string object value :: %s",
              object);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, var4);
    }
  }

  public <T> T deserialize(byte[] bytes, Class<T> clazz) {
    try {
      return CollectionUtil.isNullOrEmpty(bytes) ? null : mapper.readValue(bytes, clazz);
    } catch (Exception var5) {
      String logMessage =
          String.format(
              "JsonUtil :: deserialize : Error in deserializing byte array to class : %s",
              clazz.toString());
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, var5);
    }
  }
}

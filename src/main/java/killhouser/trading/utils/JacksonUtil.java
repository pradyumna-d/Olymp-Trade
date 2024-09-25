package killhouser.trading.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irex.servlet.logging.util.StringUtil;
import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonUtil {

  @Getter private static ObjectMapper mapper;

  public static void setup(ObjectMapper objectMapper) {
    mapper = objectMapper;
  }

  public static <T> T deserialize(Object json, Class<T> clazz) {
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

  public static <T> T deserialize(String json, Class<T> clazz) {
    try {
      if (StringUtil.isStringNullOrEmpty(json)) {
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

  public static <T> T deserialize(String json, TypeReference<T> clazz) {
    if (StringUtil.isStringNullOrEmpty(json)) {
      return null;
    }
    try {
      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "JsonUtil :: deserialize : Error in deserializing json object with typeReference :: %s",
              json);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public static <T> T deserialize(InputStream inputStream, TypeReference<T> clazz) {
    if (Objects.isNull(inputStream)) {
      return null;
    }
    try {
      return mapper.readValue(inputStream, clazz);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "JsonUtil :: deserialize : Error in deserializing inputStream with typeReference :: %s",
              inputStream);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public static Map<String, Object> convertToMap(Object source) {
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

  public static String serialize(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (Exception e) {
      String logMessage = String.format("Error in serializing string object value :: %s", object);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in serialization", logMessage, e);
    }
  }

  public static byte[] convertToBytes(Object object) {
    try {
      return mapper.writeValueAsBytes(object);
    } catch (IOException e) {
      String logMessage =
          String.format(
              "JsonUtil :: convertToBytes : Error in deserializing string object value :: %s",
              object);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
    try {
      if (CollectionUtil.isNullOrEmpty(bytes)) {
        return null;
      }
      return mapper.readValue(bytes, clazz);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "JsonUtil :: deserialize : Error in deserializing byte array to class : %s",
              clazz.toString());
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }

  public static <T> T deserialize(InputStream inputStream, Class<T> clazz) {
    if (Objects.isNull(inputStream)) {
      return null;
    }
    try {
      return mapper.readValue(inputStream, clazz);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "JsonUtil :: deserialize : Error in deserializing inputStream with clazz :: %s",
              clazz);
      throw BaseException.create(
          ResponseCode.JSON_ERROR, "error in deserialization", logMessage, e);
    }
  }
}

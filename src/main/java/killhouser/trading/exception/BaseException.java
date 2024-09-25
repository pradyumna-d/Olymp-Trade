package killhouser.trading.exception;

import java.util.logging.Level;
import java.util.logging.Logger;
import killhouser.trading.utils.StringUtils;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  private ErrorCode errorCode;
  private ErrorType type;
  private String code;
  private int httpStatusCode;

  private static final Logger logger = Logger.getLogger("BaseException");

  @Builder
  public BaseException(ErrorCode errorCode, String message, Throwable throwable) {
    super(message, throwable);
    this.httpStatusCode = errorCode.httpStatusCode();
    this.type = errorCode.errorType();
    this.code = errorCode.toString();
    this.errorCode = errorCode;
  }

  public BaseException(
      ErrorType type, String code, int httpStatusCode, String message, Throwable throwable) {
    super(message, throwable);
    this.type = type;
    this.code = code;
    this.httpStatusCode = httpStatusCode;
  }

  public static BaseException create(ErrorCode errorCode, String message) {
    return BaseException.builder().errorCode(errorCode).message(message).throwable(null).build();
  }

  public static BaseException create(
      ErrorType type, String code, int httpStatusCode, String message) {
    return new BaseException(type, code, httpStatusCode, message, null);
  }

  public static BaseException create(
      ErrorCode errorCode, String message, String logMessage, Throwable throwable) {
    if (StringUtils.isStringNullOrEmpty(logMessage)) {
      logger.log(Level.SEVERE, "error : ", throwable);
    } else {
      logger.log(Level.SEVERE, logMessage, throwable);
    }
    return BaseException.builder()
        .errorCode(errorCode)
        .message(message)
        .throwable(throwable)
        .build();
  }

  public static BaseException create(
      ErrorType type,
      String code,
      int httpStatusCode,
      String message,
      String logMessage,
      Throwable throwable) {
    if (StringUtils.isStringNullOrEmpty(logMessage)) {
      logger.log(Level.SEVERE, "error : ", throwable);
    } else {
      logger.log(Level.SEVERE, logMessage, throwable);
    }
    BaseException baseException = new BaseException(type, code, httpStatusCode, message, throwable);
    return baseException;
  }
}

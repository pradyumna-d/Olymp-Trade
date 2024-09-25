package killhouser.trading.exception;

public interface ErrorCode {

  int httpStatusCode();

  ErrorType errorType();
}

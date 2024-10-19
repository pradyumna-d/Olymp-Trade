package killhouser.trading.exception;

import static killhouser.trading.common.constants.CommonConstants.SINGLE_PIPE;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionMapper {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<BaseError> onRunTimeException(RuntimeException e) {
    log.error("runtime exception", e);
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.UNKNOWN_EXCEPTION)
            .type(ResponseCode.UNKNOWN_EXCEPTION.errorType())
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseError> onMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    List<String> errorMessages =
        e.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
            .toList();
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.INVALID_REQUEST_ERROR)
            .type(ResponseCode.INVALID_REQUEST_ERROR.errorType())
            .message(String.join(SINGLE_PIPE, errorMessages))
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<BaseError> onNullPointerException(NullPointerException e) {
    log.error("null pointer exception", e);
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.NULL_POINTER_ERROR)
            .type(ResponseCode.NULL_POINTER_ERROR.errorType())
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<BaseError> onInvalidFormatException(InvalidFormatException e) {
    String message =
        String.format(
            "The value %s format is invalid in request",
            ((InvalidFormatException) e.getCause()).getValue().toString());
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.INVALID_REQUEST_ERROR)
            .type(ResponseCode.INVALID_REQUEST_ERROR.errorType())
            .message(message)
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseError> onHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    String message = e.getMessage();
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.INVALID_REQUEST_ERROR)
            .type(ResponseCode.INVALID_REQUEST_ERROR.errorType())
            .message(message)
            .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<BaseError> onNotFoundException(NotFoundException e) {
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.NOT_FOUND)
            .type(ResponseCode.NOT_FOUND.errorType())
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<BaseError> onBaseException(BaseException e) {
    BaseError error =
        BaseError.builder()
            .code((ResponseCode) e.getErrorCode())
            .type(e.getType())
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(e.getHttpStatusCode()).body(error);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<BaseError> onIllegalArgumentException(IllegalArgumentException e) {
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.INVALID_REQUEST_ERROR)
            .type(ResponseCode.INVALID_REQUEST_ERROR.errorType())
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<BaseError> onMissingRequestHeaderException(
      MissingRequestHeaderException e) {
    String message = String.format("%s header is missing", e.getHeaderName());
    BaseError error =
        BaseError.builder()
            .code(ResponseCode.INVALID_REQUEST_ERROR)
            .type(ResponseCode.INVALID_REQUEST_ERROR.errorType())
            .message(message)
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}

package killhouser.trading.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtils {

  public static Long fetchCurrentTimestamp() {
    return System.currentTimeMillis();
  }

  public static Long fetchEpochTimeStamp(LocalDateTime localDateTime) {
    if (Objects.isNull(localDateTime)) {
      return null;
    }
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public static Long fetchEpochTimeStamp(LocalDate localDate) {
    if (Objects.isNull(localDate)) {
      return null;
    }
    return localDate
        .atTime(LocalTime.MIDNIGHT)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli();
  }

  public static long fetchEpochTimeStamp(Instant instant) {
    if (Objects.isNull(instant)) {
      return 0;
    }
    return instant.toEpochMilli();
  }

  public static LocalDate fetchDateFromEpochTimeStamp(Long epochTime) {
    if (Objects.isNull(epochTime)) {
      return null;
    }
    return Instant.ofEpochMilli(epochTime).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDateTime fetchDateTimeFromEpochTimeStamp(BigDecimal epochTime) {
    if (Objects.isNull(epochTime)) {
      return null;
    }

    // Convert BigDecimal to milliseconds and nanoseconds
    long millis = epochTime.longValue(); // Get the milliseconds part
    long nanos = epochTime.remainder(BigDecimal.ONE) // Get the fractional part
            .multiply(BigDecimal.valueOf(1_000_000_000L)) // Convert to nanoseconds
            .longValue();

    // Create Instant using millis and nanos, then convert to LocalDateTime
    Instant instant = Instant.ofEpochSecond(millis / 1000, nanos);
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
  }


}

package killhouser.trading.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

  public static boolean isStringNullOrEmpty(final String str) {
    return str == null || str.trim().isEmpty();
  }

  public static String concatenateStrings(String delimiter, String... strings) {
    if (Objects.isNull(strings)) {
      return null;
    }
    List<String> validatedStrings = new ArrayList<>();
    for (String string : strings) {
      if (Objects.nonNull(string)) {
        validatedStrings.add(string);
      }
    }
    return String.join(delimiter, validatedStrings);
  }

  public static String concatenateStrings(String delimiter, Set<String> strings) {
    if (Objects.isNull(strings)) {
      return null;
    }
    Set<String> validatedStrings = new HashSet<>();
    for (String string : strings) {
      if (Objects.nonNull(string)) {
        validatedStrings.add(string);
      }
    }
    return String.join(delimiter, validatedStrings);
  }

  public static String generateRandomNumericString(int length) {
    return RandomStringUtils.randomNumeric(length);
  }

  public static String generateRandomAlphaNumericString(int length) {
    return RandomStringUtils.randomAlphanumeric(length);
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
    for (byte b : bytes) {
      hexStringBuilder.append(String.format("%02x", b));
    }
    return hexStringBuilder.toString();
  }

  public static <T> String convertCollectionToCommaSeparatedString(
      Collection<T> collection, String delimiter) {
    return collection.stream().map(Object::toString).collect(Collectors.joining(delimiter));
  }

  public static String toSnakeCase(String input) {
    // null check
    if (Objects.isNull(input)) {
      return null;
    }
    // Step 1: Replace spaces and punctuation with underscores
    String intermediateStr = input.replaceAll("[\\s\\p{Punct}]+", "_");

    // Step 2: Insert underscores before capital letters
    // Using a regular expression to find capital letters and replace them with _<letter>
    Pattern p = Pattern.compile("([a-z])([A-Z])");
    Matcher m = p.matcher(intermediateStr);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      m.appendReplacement(sb, m.group(1) + "_" + m.group(2).toLowerCase());
    }
    m.appendTail(sb);

    // Step 3: Convert to lower case
    return sb.toString().toLowerCase();
  }
}

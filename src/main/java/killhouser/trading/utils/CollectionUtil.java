package killhouser.trading.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtil {

  public static boolean isNullOrEmpty(final Collection<?> c) {
    return c == null || c.isEmpty();
  }

  public static boolean isNullOrEmpty(final Map<?, ?> m) {
    return m == null || m.isEmpty();
  }

  public static boolean isNullOrEmpty(final byte[] bytes) {
    return bytes == null || bytes.length == 0;
  }

  public static <T> Collection<T> mergeCollections(Collection<? extends Collection<T>> lists) {
    Collection<T> result = new ArrayList<>();
    for (Collection<T> list : lists) {
      result.addAll(list);
    }
    return result;
  }

  public static <T> boolean containsElement(T element, Collection<T> collection) {
    return collection.contains(element);
  }

  /**
   * Converts a source collection to a target collection using a mapper function and collector.
   *
   * @param source The source collection to convert.
   * @param mapper Function to map elements from source to target type.
   * @param collector Collector to accumulate mapped elements into target collection.
   */
  public static <T, R, C extends Collection<R>> C convertCollection(
      Collection<? extends T> source,
      Function<? super T, ? extends R> mapper,
      Collector<? super R, ?, C> collector) {
    return source.stream().map(mapper).collect(collector);
  }
}

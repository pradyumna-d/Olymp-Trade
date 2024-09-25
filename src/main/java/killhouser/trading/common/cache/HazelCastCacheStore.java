package killhouser.trading.common.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.LocalMapStats;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import killhouser.trading.models.cache.MapStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HazelCastCacheStore {

  @Autowired private HazelcastInstance hazelcastInstance;
  public static final String INVALID_KEY_FOR_CACHE = "Invalid key for cache";

  public <T> void put(MapStore mapStore, String key, T value, long ttl) {
    if (Objects.isNull(key)) {
      log.error("Invalid input to store in cache as key is null");
      throw BaseException.create(ResponseCode.CACHE_ERROR, INVALID_KEY_FOR_CACHE);
    }
    try {
      IMap<String, T> map = hazelcastInstance.getMap(mapStore.getHazelcastMapName());
      map.putIfAbsent(key, value, ttl, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "HazelCastCacheStore :: exception while saving value in cache with key : %s , mapStore : %s",
              key, mapStore);
      throw BaseException.create(ResponseCode.CACHE_ERROR, "cache save exception", logMessage, e);
    }
  }

  public <T> void override(MapStore mapStore, String key, T value, long ttl) {
    if (Objects.isNull(key)) {
      log.error("Invalid input to store in cache as key is null");
      throw BaseException.create(ResponseCode.CACHE_ERROR, INVALID_KEY_FOR_CACHE);
    }
    try {
      IMap<String, T> map = hazelcastInstance.getMap(mapStore.getHazelcastMapName());
      map.put(key, value, ttl, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "HazelCastCacheStore :: exception while saving value in cache with key : %s , mapStore : %s",
              key, mapStore);
      throw BaseException.create(ResponseCode.CACHE_ERROR, "cache save exception", logMessage, e);
    }
  }

  public <T> Optional<T> get(MapStore mapStore, String key) {
    try {
      IMap<String, T> map = hazelcastInstance.getMap(mapStore.getHazelcastMapName());
      T value = map.get(key);
      return Optional.ofNullable(value);
    } catch (Exception e) {
      String logMessage =
          String.format(
              "HazelCastCacheStore :: exception while fetching value in cache with key : %s , mapStore : %s",
              key, mapStore);
      throw BaseException.create(ResponseCode.CACHE_ERROR, "cache fetch exception", logMessage, e);
    }
  }

  public boolean evict(MapStore mapStore, String key) {
    if (Objects.isNull(key)) {
      log.error("Invalid input to delete in cache as key is null");
      throw BaseException.create(ResponseCode.CACHE_ERROR, INVALID_KEY_FOR_CACHE);
    }
    try {
      IMap<String, String> map = hazelcastInstance.getMap(mapStore.getHazelcastMapName());
      return map.evict(key);
    } catch (Exception e) {
      log.error(
          "HazelCastCacheStore :: exception while invalidating value in cache with key : {}",
          key,
          e);
      return false;
    }
  }

  public LocalMapStats fetchCacheStats(MapStore mapStore) {
    IMap<Object, Object> map = hazelcastInstance.getMap(mapStore.getHazelcastMapName());
    return map.getLocalMapStats();
  }

  public Set<String> fetchKeySet(MapStore mapStore) {
    IMap<String, Object> map = hazelcastInstance.getMap(mapStore.getHazelcastMapName());
    return map.keySet();
  }

  public void evictAll(MapStore mapStore) {
    IMap<String, Object> map = hazelcastInstance.getMap(mapStore.getHazelcastMapName());
    map.clear();
  }
}

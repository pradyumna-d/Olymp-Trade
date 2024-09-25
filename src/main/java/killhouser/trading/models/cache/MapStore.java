package killhouser.trading.models.cache;

import lombok.Getter;

/*
    IMP: Do update hazelcastMapName is updated -->
* */
public enum MapStore {
  TIME_CACHE("Time");

  @Getter private final String hazelcastMapName;

  MapStore(String hazelcastMapName) {
    this.hazelcastMapName = hazelcastMapName;
  }
}

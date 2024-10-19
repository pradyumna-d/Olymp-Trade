package killhouser.trading.models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Index {
  COMMODITY("CMDTY_X");
  String value;

  public String getValue() {
    return this.value;
  }
}

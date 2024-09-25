package killhouser.trading.config.command;

import lombok.Getter;

public enum ClientType {
  OLYMP_TRADE("olymp-trade");

  @Getter private final String clientName;

  ClientType(String clientName) {
    this.clientName = clientName;
  }

  public static final String OLYMP_TRADE_CLIENT_NAME = "olymp-trade";
}

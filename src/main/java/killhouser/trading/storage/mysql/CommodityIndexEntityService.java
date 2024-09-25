package killhouser.trading.storage.mysql;

import java.util.List;
import killhouser.trading.exception.BaseException;
import killhouser.trading.exception.ResponseCode;
import killhouser.trading.storage.mysql.entity.commodity.CommodityIndexEntity;
import killhouser.trading.storage.mysql.repository.CommodityIndexRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommodityIndexEntityService {

  @Autowired CommodityIndexRepository commodityIndexRepository;

  public void saveAll(List<CommodityIndexEntity> commodityIndexEntities) {
    try {
      commodityIndexRepository.saveAll(commodityIndexEntities);
    } catch (Exception e) {
      String logMessage =
          String.format("Unable to save commodity index prices! :: %s", commodityIndexEntities);
      throw BaseException.create(
          ResponseCode.MYSQL_ERROR, "Unable to save commodity index prices!", logMessage, e);
    }
  }
}

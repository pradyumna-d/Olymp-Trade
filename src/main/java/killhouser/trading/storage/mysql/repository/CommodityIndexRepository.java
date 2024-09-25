package killhouser.trading.storage.mysql.repository;

import killhouser.trading.storage.mysql.entity.commodity.CommodityIndexEntity;
import killhouser.trading.storage.mysql.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityIndexRepository extends BaseRepository<CommodityIndexEntity, Long> {}

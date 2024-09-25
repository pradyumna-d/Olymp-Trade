package killhouser.trading.storage.mysql.entity.commodity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import killhouser.trading.storage.mysql.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "commodity_composite_index")
@EqualsAndHashCode(callSuper = true)
public class CommodityIndexEntity extends BaseEntity {

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @Column(name = "epoch")
    private Double epochTimeStamp;

    @Column(name = "price")
    private Double price;
}

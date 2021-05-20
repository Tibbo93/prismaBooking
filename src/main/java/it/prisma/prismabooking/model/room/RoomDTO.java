package it.prisma.prismabooking.model.room;

import it.prisma.prismabooking.model.building.Building;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDTO {

    private Integer id;
    private BigDecimal size;
    private BigDecimal priceLowSeason;
    private BigDecimal priceMidSeason;
    private BigDecimal priceHighSeason;
    private boolean flagBalcony;
    private boolean flagShower;
    private boolean flagWhirlpool;
    private RoomType category;
    private BigDecimal singleBeds;
    private BigDecimal queenSizeBeds;
    private BigDecimal bathrooms;
    private Building building;
}

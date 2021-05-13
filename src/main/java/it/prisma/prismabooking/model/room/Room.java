package it.prisma.prismabooking.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Room {
    private String id;
    private Double size;
    private Double priceLowSeason;
    private Double priceMidSeason;
    private Double priceHighSeason;
    private boolean flagBalcony;
    private boolean flagShower;
    private boolean flagWhirlpool;
    private RoomType category;
    private Integer singleBeds;
    private Integer queenSizeBeds;
    private Integer bathrooms;
    private String buildingId;
}

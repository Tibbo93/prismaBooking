package it.prisma.prismabooking.model.room;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.utils.BuildingSerializer;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal size;

    @Column(name = "p_low_s")
    private BigDecimal priceLowSeason;

    @Column(name = "p_middle_s")
    private BigDecimal priceMidSeason;

    @Column(name = "p_high_s")
    private BigDecimal priceHighSeason;

    @Column(name = "flag_balcony")
    private Boolean flagBalcony;

    @Column(name = "flag_shower")
    private Boolean flagShower;

    @Column(name = "flag_whirlpool")
    private Boolean flagWhirlpool;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('economy', 'family', 'junior_suite', 'deluxe_suite')", nullable = false)
    private RoomType category;

    @Column(name = "n_single_beds")
    private BigDecimal singleBeds;

    @Column(name = "n_queen_size_beds")
    private BigDecimal queenSizeBeds;

    @Column(name = "n_bathrooms")
    private BigDecimal bathrooms;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    @JsonSerialize(using = BuildingSerializer.class)
    private Building building;
}
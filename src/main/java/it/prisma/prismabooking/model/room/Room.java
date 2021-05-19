package it.prisma.prismabooking.model.room;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.prisma.prismabooking.model.building.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "id")
    private Integer id;

    @Column(name = "size")
    private BigDecimal size;

    @Column(name = "p_low_s")
    private BigDecimal priceLowSeason;

    @Column(name = "p_middle_s")
    private BigDecimal priceMidSeason;

    @Column(name = "p_high_s")
    private BigDecimal priceHighSeason;

    @Column(name = "flag_balcony")
    private boolean flagBalcony;

    @Column(name = "flag_shower")
    private boolean flagShower;

    @Column(name = "flag_whirlpool")
    private boolean flagWhirlpool;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition ="ENUM('economy', 'family', 'junior_suite', 'deluxe_suite')", nullable = false )
    private RoomType category;

    @Column(name = "n_single_beds")
    private BigDecimal singleBeds;

    @Column(name = "n_queen_size_beds")
    private BigDecimal queenSizeBeds;

    @Column(name = "n_bathrooms")
    private BigDecimal bathrooms;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;
}
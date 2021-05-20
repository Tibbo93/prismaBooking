package it.prisma.prismabooking.model.facility;

import com.fasterxml.jackson.annotation.*;
import it.prisma.prismabooking.model.building.Building;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "service")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "flag_luxury")
    private Boolean flagLuxury;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "facilities")
    private Set<Building> buildings = new HashSet<>();
}
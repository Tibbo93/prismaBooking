package it.prisma.prismabooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.prisma.prismabooking.model.building.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Facility {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "flag_luxury")
    private boolean flagLuxury;

    @ManyToMany(mappedBy = "facilities")
    @JsonBackReference
    private Set<Building> buildings = new HashSet<>();
}
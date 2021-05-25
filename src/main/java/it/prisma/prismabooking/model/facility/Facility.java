package it.prisma.prismabooking.model.facility;

import com.fasterxml.jackson.annotation.*;
import it.prisma.prismabooking.model.building.Building;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Facility.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String name;

    private BigDecimal price;

    @Column(name = "flag_luxury")
    private Boolean flagLuxury;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "facilities", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Building> buildings = new HashSet<>();
}
package it.prisma.prismabooking.model.building;

import com.fasterxml.jackson.annotation.*;
import it.prisma.prismabooking.model.facility.Facility;
import it.prisma.prismabooking.model.room.Room;
import it.prisma.prismabooking.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "building")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Building.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    private String email;

    @Column(name = "flag_wifi")
    private boolean flagWiFi;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "ENUM('economy', 'family', 'junior_suite', 'deluxe_suite')", nullable = false)
    private BuildingType buildingType;

    private String street;

    private String city;

    private String country;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Room> rooms = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "works",
            joinColumns = {@JoinColumn(name = "building_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "offers",
            joinColumns = {@JoinColumn(name = "building_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    @JsonBackReference
    private Set<Facility> facilities = new HashSet<>();
}
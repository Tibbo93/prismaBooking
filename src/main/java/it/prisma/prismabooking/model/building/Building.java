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
    private Boolean flagWiFi;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "ENUM('economy', 'family', 'junior_suite', 'deluxe_suite')", nullable = false)
    private BuildingType buildingType;

    private String street;

    private String city;

    private String country;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Room> rooms = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "works",
            joinColumns = {@JoinColumn(name = "building_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonIdentityReference(alwaysAsId = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<User> users = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "offers",
            joinColumns = {@JoinColumn(name = "building_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    @JsonIdentityReference(alwaysAsId = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Facility> facilities = new HashSet<>();

    public Building(Integer id, String telephoneNumber, String email, Boolean flagWiFi,
                    BuildingType buildingType, String street, String city, String country) {
        this.id = id;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.flagWiFi = flagWiFi;
        this.buildingType = buildingType;
        this.street = street;
        this.city = city;
        this.country = country;
    }
}
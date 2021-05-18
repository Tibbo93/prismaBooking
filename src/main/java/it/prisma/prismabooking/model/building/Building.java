package it.prisma.prismabooking.model.building;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.prisma.prismabooking.model.Facility;
import it.prisma.prismabooking.model.room.Room;
import it.prisma.prismabooking.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
public class Building {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "flag_wifi")
    private boolean flagWiFi;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "ENUM('economy', 'family', 'junior_suite', 'deluxe_suite')", nullable = false)
    private BuildingType buildingType;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "works",
            joinColumns = {@JoinColumn(name = "building_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "offers",
            joinColumns = {@JoinColumn(name = "building_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    @JsonManagedReference
    private Set<Facility> facilities = new HashSet<>();
}
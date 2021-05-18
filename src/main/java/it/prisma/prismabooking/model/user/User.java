package it.prisma.prismabooking.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.prisma.prismabooking.model.building.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "ENUM('amministratore', 'manager', 'cliente', 'receptionist')", nullable = false)
    private UserRole role;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @ManyToMany(mappedBy = "users")
    @JsonBackReference
    private Set<Building> buildings = new HashSet<>();
}
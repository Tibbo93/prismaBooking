package it.prisma.prismabooking.repository;

import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.building.BuildingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    //METODO 1: utilizzo interfaccia con i getter degli attributi che voglio
    @Query("SELECT b FROM Building b JOIN b.users u WHERE u.id=:userId")
    Page<BuildingProjection> findAllByUserId(Pageable pageable, @Param("userId") Integer userId);

    //METODO 2: utilizzo annotazione @JsonInclude(JsonInclude.Include.NON_EMPTY) sulla propietà dell'entità
    // e utilizzo un costruttore con solo gli argomenti cercati
    @Query("SELECT new Building(b.id, b.telephoneNumber, b.email, b.flagWiFi, b.buildingType, b.street, b.city, b.country) " +
            "FROM Building b JOIN b.facilities f " +
            "WHERE f.id=:facilityId")
    Page<Building> findAllByFacilitiesId(Pageable page, @Param("facilityId") Integer facilityId);
}

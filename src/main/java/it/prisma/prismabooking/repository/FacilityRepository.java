package it.prisma.prismabooking.repository;

import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.facility.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {

    Page<Facility> findAllByBuildings(Pageable page, Building building);
}

package it.prisma.prismabooking.repository;

import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findAllByBuildings(Pageable page, Building buildingId);
}

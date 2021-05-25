package it.prisma.prismabooking.repository;

import it.prisma.prismabooking.model.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByIdAndBuilding_Id(Integer roomId, Integer buildingId);

    Page<Room> findAllByBuilding_Id(Pageable page, Integer buildingId);

    void deleteByIdAndBuilding_Id(Integer roomId, Integer buildingId);
}

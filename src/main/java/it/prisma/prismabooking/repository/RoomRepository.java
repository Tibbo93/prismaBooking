package it.prisma.prismabooking.repository;

import it.prisma.prismabooking.model.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Page<Room> findAllByBuilding_Id(Pageable page, Integer buildingId);
}

package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.room.Room;
import it.prisma.prismabooking.repository.RoomRepository;
import it.prisma.prismabooking.utils.exceptions.NotFoundException;
import it.prisma.prismabooking.utils.RoomMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends BaseService<Room> {

    RoomRepository roomRepository;
    BuildingService buildingService;

    public RoomService(ConfigurationComponent configurationComponent,
                       BuildingService buildingService,
                       RoomRepository roomRepository) {
        super.config = configurationComponent;
        this.buildingService = buildingService;
        this.roomRepository = roomRepository;
    }

    public Page<Room> findPage(Integer offset, Integer limit, Integer buildingId) {
        buildingService.findResource2(buildingId);
        Pageable page = PageRequest.of(offset, limit);
        return roomRepository.findAllByBuilding_Id(page, buildingId);
    }

    public Room findRoom(Integer buildingId, Integer roomId) {
        buildingService.findResource2(buildingId);
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));
    }

    public Room createRoom(Room room, Integer buildingId) {
        Building building = buildingService.findResource2(buildingId);
        room.setBuilding(building);
        return roomRepository.save(room);
    }

    public void deleteRoom(Integer roomId, Integer buildingId) {
        buildingService.findResource2(buildingId);
        roomRepository.deleteById(roomId);
    }

    public Room updateRoom(Room room, Integer buildingId, Integer roomId) {
        buildingService.findResource2(buildingId);
        Room roomInDB = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));
        RoomMapper.INSTANCE.updateRoom(room, roomInDB);
        return roomRepository.save(roomInDB);
    }
}

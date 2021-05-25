package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.room.Room;
import it.prisma.prismabooking.repository.RoomRepository;
import it.prisma.prismabooking.utils.exceptions.InternalServerErrorException;
import it.prisma.prismabooking.utils.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class RoomService {

    ConfigurationComponent config;
    RoomRepository roomRepository;
    BuildingService buildingService;

    public RoomService(ConfigurationComponent configurationComponent,
                       BuildingService buildingService,
                       RoomRepository roomRepository) {
        this.config = configurationComponent;
        this.buildingService = buildingService;
        this.roomRepository = roomRepository;
    }

    public Page<Room> findPage(Integer offset, Integer limit, Integer buildingId) {
        buildingService.findBuilding(buildingId);
        return roomRepository.findAllByBuilding_Id(PageRequest.of(offset, limit), buildingId);
    }

    public Room findRoom(Integer buildingId, Integer roomId) {
        return roomRepository.findByIdAndBuilding_Id(roomId, buildingId)
                .orElseThrow(() -> new NotFoundException("Room not found with id " + roomId +
                        " and building id " + buildingId));
    }

    public Room createRoom(Room room, Integer buildingId) {
        room.setBuilding(buildingService.findBuilding(buildingId));
        return roomRepository.save(room);
    }

    @Transactional
    public Room updateRoom(Room room, Integer buildingId, Integer roomId) {
        Room roomInDB = findRoom(buildingId, roomId);

        Arrays.stream(room.getClass().getDeclaredFields())
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        if (f.get(room) != null)
                            f.set(roomInDB, f.get(room));
                    } catch (IllegalAccessException e) {
                        throw new InternalServerErrorException(e.getMessage());
                    }
                });

        return roomRepository.save(roomInDB);
    }

    @Transactional
    public void deleteRoom(Integer roomId, Integer buildingId) {
        findRoom(buildingId, roomId);
        roomRepository.deleteByIdAndBuilding_Id(roomId, buildingId);
    }
}

package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.room.Room;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService extends BaseService<Room> {

    @Value("file:src/main/resources/data/rooms.json")
    Resource roomsFile;
    BuildingService buildingService;

    public RoomService(ConfigurationComponent configurationComponent, BuildingService buildingService) {
        super.config = configurationComponent;
        this.buildingService = buildingService;
    }

    public PagedRes<Room> findPage(Integer offset, Integer limit, String buildingId) {
        buildingService.findResource(buildingId);
        return findPage(offset, limit);
    }

    public Room findRoom(String roomId, String buildinId) {
        buildingService.findResource(buildinId);
        return findResource(roomId);
    }

    public Room createRoom(Room room, String buildingId) {
        buildingService.findResource(buildingId);
        if (!Optional.ofNullable(room.getId()).isPresent()) {
            room.setId(UUID.randomUUID().toString());
            room.setBuildingId(buildingId);
        } else {
            findResource(room.getId());
            deleteRoom(room.getId(), buildingId);
        }

        list.add(room);
        writeOnJSON(roomsFile, room);
        return room;
    }

    public void deleteRoom(String roomId, String buildingId) {
        buildingService.findResource(buildingId);
        list.remove(findResource(roomId));
        deleteFromJSON(roomsFile);
    }

    @PostConstruct
    public void init() {
        loadJSON(roomsFile, Room.class);
    }
}

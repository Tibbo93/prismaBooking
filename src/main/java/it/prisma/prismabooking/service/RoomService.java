package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.room.Room;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Service
public class RoomService extends BaseService<Room> {

    BuildingService buildingService;

    public RoomService(ConfigurationComponent configurationComponent, BuildingService buildingService) {
        super.config = configurationComponent;
        super.resourceFile = new DefaultResourceLoader().getResource("file:src/main/resources/data/rooms.json");
        super.resourceType = "Room";
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
        return createResource(room);
    }

    public void deleteRoom(String roomId, String buildingId) {
        buildingService.findResource(buildingId);
        deleteResource(roomId);
    }

    @PostConstruct
    public void init() {
        loadJSON(Room.class);
    }
}

package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.room.Room;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService extends BaseService<Room> {

    @Value("file:src/main/resources/rooms.json")
    Resource roomsFile;

    public RoomService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
    }

    public Room createRoom(Room room) {
        if (!Optional.ofNullable(room.getId()).isPresent())
            room.setId(UUID.randomUUID().toString());
        else {
            findResource(room.getId());
            deleteRoom(room.getId());
        }

        list.add(room);
        writeOnJSON(roomsFile, room);
        return room;
    }

    public void deleteRoom(String roomId) {
        list.remove(findResource(roomId));
        deleteFromJSON(roomsFile);
    }

    @PostConstruct
    public void init() {
        loadJSON(roomsFile, Room.class);
    }
}

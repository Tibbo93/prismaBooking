package it.prisma.prismabooking.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.prisma.prismabooking.model.room.Room;
import it.prisma.prismabooking.utils.exceptions.InternalServerErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomListSerializer extends StdSerializer<List<Room>> {

    public RoomListSerializer() {
        this(null);
    }

    public RoomListSerializer(Class<List<Room>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Room> rooms, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        List<Integer> ids = new ArrayList<>();
        rooms.forEach(room -> ids.add(room.getId()));
        try {
            jsonGenerator.writeObject(ids);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}

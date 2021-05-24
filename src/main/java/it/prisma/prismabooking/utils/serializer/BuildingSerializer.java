package it.prisma.prismabooking.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.utils.exceptions.InternalServerErrorException;

import java.io.IOException;

public class BuildingSerializer extends StdSerializer<Building> {

    public BuildingSerializer() {
        this(null);
    }

    protected BuildingSerializer(Class<Building> t) {
        super(t);
    }

    @Override
    public void serialize(Building building, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
            jsonGenerator.writeObject(building.getId());
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}

package it.prisma.prismabooking.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.utils.exceptions.InternalServerErrorException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BuildingSetSerializer extends StdSerializer<Set<Building>> {

    protected BuildingSetSerializer() {
        this(null);
    }

    protected BuildingSetSerializer(Class<Set<Building>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Building> buildingSet, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        Set<Integer> ids = new HashSet<>();
        buildingSet.forEach(building -> ids.add(building.getId()));
        try {
            jsonGenerator.writeObject(ids);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}

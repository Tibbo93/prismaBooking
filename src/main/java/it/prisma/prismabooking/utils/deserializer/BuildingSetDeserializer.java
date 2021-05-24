package it.prisma.prismabooking.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.prisma.prismabooking.model.building.Building;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BuildingSetDeserializer extends StdDeserializer<Set<Building>> {

    protected BuildingSetDeserializer() {
        this(null);
    }

    protected BuildingSetDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Set<Building> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        Set<Building> buildings = new HashSet<>();
        buildings.add((new Building()));
        return buildings;
    }
}

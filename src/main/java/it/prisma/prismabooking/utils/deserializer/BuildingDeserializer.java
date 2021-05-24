package it.prisma.prismabooking.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.room.Room;

import java.io.IOException;

public class BuildingDeserializer extends StdDeserializer<Building> {

    public BuildingDeserializer() {
        this(null);
    }

    protected BuildingDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Building deserialize(JsonParser jsonParser, DeserializationContext deserializationContext){
        try {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            Integer id = (Integer) ((IntNode) node.get("id")).numberValue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Building();
    }
}

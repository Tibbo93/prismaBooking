package it.prisma.prismabooking.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.prisma.prismabooking.model.facility.Facility;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FacilitySetSerializer extends StdSerializer<Set<Facility>> {

    public FacilitySetSerializer() {
        this(null);
    }

    protected FacilitySetSerializer(Class<Set<Facility>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Facility> facilities, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        Set<Integer> ids = new HashSet<>();
        facilities.forEach(facility -> ids.add(facility.getId()));
        try {
            jsonGenerator.writeObject(ids);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}

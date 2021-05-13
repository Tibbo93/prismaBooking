package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.Facility;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacilityService extends BaseService<Facility> {

    @Value("file:src/main/resources/data/facilities.json")
    Resource facilitiesFile;

    public FacilityService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
    }

    public Facility createFacility(Facility facility) {
        if (!Optional.ofNullable(facility.getId()).isPresent())
            facility.setId(UUID.randomUUID().toString());
        else {
            findResource(facility.getId());
            deleteFacility(facility.getId());
        }

        list.add(facility);
        writeOnJSON(facilitiesFile, facility);
        return facility;
    }

    public void deleteFacility(String id) {
        list.remove(findResource(id));
        deleteFromJSON(facilitiesFile);
    }

    @PostConstruct
    public void init() {
        loadJSON(facilitiesFile, Facility.class);
    }
}

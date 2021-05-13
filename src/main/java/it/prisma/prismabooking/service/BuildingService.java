package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.building.Building;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuildingService extends BaseService<Building> {

    @Value("file:src/main/resources/data/buildings.json")
    Resource buildingsFile;

    public BuildingService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
    }

    public Building createBuilding(Building building) {
        if (!Optional.ofNullable(building.getId()).isPresent())
            building.setId(UUID.randomUUID().toString());
        else {
            findResource(building.getId());
            deleteBuilding(building.getId());
        }

        list.add(building);
        writeOnJSON(buildingsFile, building);
        return building;
    }

    public void deleteBuilding(String buildingId) {
        list.remove(findResource(buildingId));
        deleteFromJSON(buildingsFile);
    }

    @PostConstruct
    public void init() {
        loadJSON(buildingsFile, Building.class);
    }
}


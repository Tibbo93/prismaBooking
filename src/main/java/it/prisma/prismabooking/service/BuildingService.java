package it.prisma.prismabooking.service;

import com.google.gson.Gson;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BuildingService extends BaseService<Building> {

    @Value("file:src/main/resources/data/buildings.json")
    Resource buildingsFile;
    Gson gson;

    public BuildingService(ConfigurationComponent configurationComponent, Gson gson) {
        super.config = configurationComponent;
        this.gson = gson;
    }

    public Building findBuilding(String buildingId) {
        return list.stream()
                .filter(building -> building.getId().equalsIgnoreCase(buildingId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Building not found with id: " + buildingId));
    }

    public Building createBuilding(Building building) {
        if (!Optional.ofNullable(building.getId()).isPresent())
            building.setId(UUID.randomUUID().toString());
        else {
            findBuilding(building.getId());
            deleteBuilding(building.getId());
        }

        list.add(building);
        try {
            Files.write(buildingsFile.getFile().toPath(),
                    gson.toJson(building).concat(System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return building;
    }

    public void deleteBuilding(String buildingId) {
        list.remove(findBuilding(buildingId));

        try {
            Files.write(buildingsFile.getFile().toPath(), list.stream()
                            .map(building -> gson.toJson(building))
                            .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator())).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        if (!buildingsFile.exists())
            return;
        try (Stream<String> lines = Files.lines(buildingsFile.getFile().toPath())) {
            lines.forEach(line -> list.add(gson.fromJson(line, Building.class)));
        } catch (IOException e) {
            log.error("Error parsing line with cause: {}", e.getMessage());
        }
    }
}


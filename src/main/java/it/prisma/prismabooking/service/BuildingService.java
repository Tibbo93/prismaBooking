package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuildingService extends BaseService<Building> {

    @Value("file:src/main/resources/data/buildings.json")
    Resource buildingsFile;
    UserService userService;

    public BuildingService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
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

        userService.list
                .forEach(user -> user.getBuildingsId().remove(buildingId));
        userService.deleteFromJSON(userService.usersFile);
    }

    public PagedRes<Building> findBuildingsByUser(Integer offset, Integer limit, String userId) {
        List<Building> buildings = new ArrayList<Building>();
        User user = userService.findResource(userId);
        user.getBuildingsId()
                .forEach(buildingId -> buildings.add(findResource(buildingId)));
        return findPage(offset, limit, buildings);
    }

    @PostConstruct
    public void init() {
        loadJSON(buildingsFile, Building.class);
    }
}


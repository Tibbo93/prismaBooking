package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService extends BaseService<Building> {

    UserService userService;
    FacilityService facilityService;

    public BuildingService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
        super.resourceFile = new DefaultResourceLoader().getResource("file:src/main/resources/data/buildings.json");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    public void deleteBuilding(String buildingId) {
        deleteResource(buildingId);

        userService.list
                .forEach(user -> user.getBuildingsId().remove(buildingId));
        userService.deleteFromJSON();
    }

    public PagedRes<Building> findBuildingsOfUser(Integer offset, Integer limit, String userId) {
        List<Building> buildings = new ArrayList<>();
        User user = userService.findResource(userId);
        user.getBuildingsId()
                .forEach(buildingId -> buildings.add(findResource(buildingId)));
        return findPage(offset, limit, buildings);
    }

    public PagedRes<Building> findBuildingsOfFacility(Integer offset, Integer limit, String facilityId) {
        return findPage(offset, limit, list.stream()
                .filter(building -> building.getFacilitiesId().contains(facilityId))
                .collect(Collectors.toList()));
    }

    @PostConstruct
    public void init() {
        loadJSON(Building.class);
    }
}


package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.building.BuildingDTO;
import it.prisma.prismabooking.repository.BuildingRepository;
import it.prisma.prismabooking.utils.BuildingMapper;
import it.prisma.prismabooking.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuildingService extends BaseService<Building>{

    UserService userService;
    FacilityService facilityService;
    private final BuildingRepository buildingRepository;

    public BuildingService(ConfigurationComponent configurationComponent, BuildingRepository buildingRepository) {
        super.config = configurationComponent;
        super.resourceFile = new DefaultResourceLoader().getResource("file:src/main/resources/data/buildings.json");
        super.resourceType = "Building";
        this.buildingRepository = buildingRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    public Page<Building> findPage2(Integer offset, Integer limit) {
        Pageable page = PageRequest.of(offset, limit);
        return buildingRepository.findAll(page);
    }

    public Building findResource2(Integer id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Building not found with id: " + id));
    }

    public Building createResource2(Building building) {
        return buildingRepository.save(building);
    }

    public void deleteBuilding(Integer buildingId) {
        buildingRepository.deleteById(buildingId);
    }

    public Building updateBuilding(BuildingDTO buildingDTO, Integer buildingId) {
        Building buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new NotFoundException("Building not found with id: " + buildingDTO.getId()));

        BuildingMapper.INSTANCE.updateBuildingFromDTO(buildingDTO, buildingEntity);
        return buildingRepository.save(buildingEntity);
    }

    public PagedRes<Building> findBuildingsOfUser(Integer offset, Integer limit, String userId) {
        return null;
        /*List<Building> buildings = new ArrayList<>();
        User user = userService.findResource(userId);
        user.getBuildingsId()
                .forEach(buildingId -> buildings.add(findResource(buildingId)));
        return findPage(offset, limit, buildings);*/
    }

    public PagedRes<Building> findBuildingsOfFacility(Integer offset, Integer limit, String facilityId) {
        return null;
        /*return findPage(offset, limit, list.stream()
                .filter(building -> building.getFacilitiesId().contains(facilityId))
                .collect(Collectors.toList()));*/
    }
}


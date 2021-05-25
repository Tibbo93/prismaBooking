package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.building.BuildingProjection;
import it.prisma.prismabooking.repository.BuildingRepository;
import it.prisma.prismabooking.utils.exceptions.InternalServerErrorException;
import it.prisma.prismabooking.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class BuildingService {

    ConfigurationComponent config;
    UserService userService;
    FacilityService facilityService;
    private final BuildingRepository buildingRepository;

    public BuildingService(ConfigurationComponent configurationComponent, BuildingRepository buildingRepository) {
        this.config = configurationComponent;
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

    public Page<Building> findPage(Integer offset, Integer limit) {
        return buildingRepository.findAll(PageRequest.of(offset, limit));
    }

    public Building findBuilding(Integer id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Building not found with id: " + id));
    }

    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public void deleteBuilding(Integer buildingId) {
        findBuilding(buildingId);
        buildingRepository.deleteById(buildingId);
    }

    @Transactional
    public Building updateBuilding(Building building, Integer buildingId) {
        Building buildingInDB = findBuilding(buildingId);

        Arrays.stream(building.getClass().getDeclaredFields())
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        if (f.get(building) != null)
                            f.set(buildingInDB, f.get(building));
                    } catch (IllegalAccessException e) {
                        throw new InternalServerErrorException(e.getMessage());
                    }
                });

        return buildingRepository.save(buildingInDB);
    }

    public Page<BuildingProjection> findBuildingsOfUser(Integer offset, Integer limit, Integer userId) {
        return buildingRepository.findAllByUserId(PageRequest.of(offset, limit), userId);
    }

    public Page<Building> findBuildingsOfFacility(Integer offset, Integer limit, Integer facilityId) {
        Pageable page = PageRequest.of(offset, limit);
        return buildingRepository.findAllByFacilitiesId(page, facilityId);
    }
}


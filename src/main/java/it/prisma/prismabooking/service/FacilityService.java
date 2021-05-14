package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.Facility;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.building.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacilityService extends BaseService<Facility> {

    @Value("file:src/main/resources/data/facilities.json")
    Resource facilitiesFile;
    BuildingService buildingService;

    public FacilityService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
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

    public void deleteFacility(String facilityId) {
        list.remove(findResource(facilityId));
        deleteFromJSON(facilitiesFile);

        buildingService.list
                .forEach(building -> building.getFacilitiesId().remove(facilityId));
        buildingService.deleteFromJSON(buildingService.buildingsFile);
    }

    public PagedRes<Facility> findFacilitiesOfBuilding(Integer offset, Integer limit, String buildingId) {
        List<Facility> facilities = new ArrayList<>();
        Building b = buildingService.findResource(buildingId);
        b.getFacilitiesId()
                .forEach(facilityId -> facilities.add(findResource(facilityId)));
        return findPage(offset, limit, facilities);
    }

    @PostConstruct
    public void init() {
        loadJSON(facilitiesFile, Facility.class);
    }
}

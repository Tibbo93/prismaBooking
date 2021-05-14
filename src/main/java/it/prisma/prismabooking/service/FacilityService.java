package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.Facility;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.building.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityService extends BaseService<Facility> {

    BuildingService buildingService;

    public FacilityService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
        super.resourceFile = new DefaultResourceLoader().getResource("file:src/main/resources/data/facilities.json");
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public void deleteFacility(String facilityId) {
        deleteResource(facilityId);

        buildingService.list
                .forEach(building -> building.getFacilitiesId().remove(facilityId));
        buildingService.deleteFromJSON();
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
        loadJSON(Facility.class);
    }
}

package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.facility.Facility;
import it.prisma.prismabooking.model.PagedRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class FacilityService extends BaseService<Facility> {

    BuildingService buildingService;

    public FacilityService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
        super.resourceFile = new DefaultResourceLoader().getResource("file:src/main/resources/data/facilities.json");
        super.resourceType = "Facility";
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public void deleteFacility(String facilityId) {
        /*deleteResource(facilityId);

        buildingService.list
                .forEach(building -> building.getFacilitiesId().remove(facilityId));
        buildingService.deleteFromJSON();*/
    }

    public PagedRes<Facility> findFacilitiesOfBuilding(Integer offset, Integer limit, String buildingId) {
        return null;
        /*List<Facility> facilities = new ArrayList<>();
        Building b = buildingService.findResource(buildingId);
        b.getFacilitiesId()
                .forEach(facilityId -> facilities.add(findResource(facilityId)));
        return findPage(offset, limit, facilities);*/
    }
}

package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.facility.Facility;
import it.prisma.prismabooking.repository.BuildingRepository;
import it.prisma.prismabooking.repository.FacilityRepository;
import it.prisma.prismabooking.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FacilityService extends BaseService<Facility> {

    FacilityRepository facilityRepository;
    BuildingService buildingService;

    public FacilityService(ConfigurationComponent configurationComponent, FacilityRepository facilityRepository) {
        super.config = configurationComponent;
        super.resourceFile = new DefaultResourceLoader().getResource("file:src/main/resources/data/facilities.json");
        super.resourceType = "Facility";
        this.facilityRepository = facilityRepository;
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public Page<Facility> findPage2(Integer offset, Integer limit) {
        Pageable page = PageRequest.of(offset, limit);
        return this.facilityRepository.findAll(page);
    }

    public Facility findFacility(Integer id) {
        return this.facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Facility not found with id: " + id));
    }

    public Facility createFacility(Facility facility) {
        return this.facilityRepository.save(facility);
    }

    public void deleteFacility(Integer facilityId) {
        this.facilityRepository.deleteById(facilityId);
    }

    public Facility updateFacility(Facility facility) {
        Facility facilityInDB = this.facilityRepository.findById(facility.getId())
                .orElseThrow(() -> new NotFoundException("Facility not found with id: " + facility.getId()));

        if (Optional.ofNullable(facility.getName()).isPresent())
            facilityInDB.setName(facility.getName());

        if (Optional.ofNullable(facility.getPrice()).isPresent())
            facilityInDB.setPrice(facility.getPrice());

        if (Optional.ofNullable(facility.getFlagLuxury()).isPresent())
            facilityInDB.setFlagLuxury(facility.getFlagLuxury());

        return this.facilityRepository.save(facilityInDB);
    }

    public Page<Facility> findFacilitiesOfBuilding(Integer offset, Integer limit, Integer buildingId) {
        Building building = this.buildingService.findResource2(buildingId);
        Pageable page = PageRequest.of(offset, limit);
        return this.facilityRepository.findAllByBuildings(page, building);
    }
}

package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.facility.Facility;
import it.prisma.prismabooking.repository.FacilityRepository;
import it.prisma.prismabooking.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FacilityService {

    ConfigurationComponent config;
    FacilityRepository facilityRepository;
    BuildingService buildingService;

    public FacilityService(ConfigurationComponent configurationComponent, FacilityRepository facilityRepository) {
        this.config = configurationComponent;
        this.facilityRepository = facilityRepository;
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public Page<Facility> findPage(Integer offset, Integer limit) {
        return facilityRepository.findAll(PageRequest.of(offset, limit));
    }

    public Facility findFacility(Integer id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Facility not found with id: " + id));
    }

    public Facility createFacility(Facility facility) {
        return facilityRepository.save(facility);
    }

    public void deleteFacility(Integer facilityId) {
        findFacility(facilityId);
        facilityRepository.deleteById(facilityId);
    }

    @Transactional
    public Facility updateFacility(Facility facility, Integer facilityId) {
        Facility facilityInDB = findFacility(facilityId);

        if (Optional.ofNullable(facility.getName()).isPresent())
            facilityInDB.setName(facility.getName());

        if (Optional.ofNullable(facility.getPrice()).isPresent())
            facilityInDB.setPrice(facility.getPrice());

        if (Optional.ofNullable(facility.getFlagLuxury()).isPresent())
            facilityInDB.setFlagLuxury(facility.getFlagLuxury());

        return facilityRepository.save(facilityInDB);
    }

    public Page<Facility> findFacilitiesOfBuilding(Integer offset, Integer limit, Integer buildingId) {
        Building building = buildingService.findBuilding(buildingId);
        Pageable page = PageRequest.of(offset, limit);
        return facilityRepository.findAllByBuildings(page, building);
    }
}

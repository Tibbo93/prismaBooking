package it.prisma.prismabooking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.prisma.prismabooking.model.facility.Facility;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.facility.FacilityDTO;
import it.prisma.prismabooking.service.FacilityService;
import it.prisma.prismabooking.utils.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
@Tag(name = "facilities", description = "The Facility API")
public class FacilityController {

    @Autowired
    ModelMapper modelMapper;
    final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @Operation(summary = "Get facility list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content)})
    @GetMapping("/facilities")
    @ResponseStatus(HttpStatus.OK)
    public Page<FacilityDTO> findPage(@Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                      @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        Page<Facility> page = facilityService.findPage2(offset, limit);
        return page.map(this::convertToDTO);
    }

    @Operation(summary = "Add new facility",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Facility object that needs to be added", required = true),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "405", description = "Invalid input",
                            content = @Content)})
    @PostMapping("/facilities")
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDTO createFacility(@RequestBody FacilityDTO facilityDTO) {
        if (facilityDTO.getId() != null)
            throw new BadRequestException("Cannot POST resource that already have an ID");
        Facility facility = convertToEntity(facilityDTO);
        return convertToDTO(facilityService.createFacility(facility));
    }

    @Operation(summary = "Find facility by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Facility.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Facility not found",
                            content = @Content)})
    @GetMapping("/facilities/{facilityId}")
    @ResponseStatus(HttpStatus.OK)
    public FacilityDTO findFacility(@Parameter(description = "ID of a facility") @PathVariable("facilityId") Integer facilityId) {
        return convertToDTO(facilityService.findFacility(facilityId));
    }

    @Operation(summary = "Update existing facility",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Facility object that needs to be updated", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Facility not found",
                            content = @Content)})
    @PutMapping("/facilities/{facilityId}")
    @ResponseStatus(HttpStatus.OK)
    public FacilityDTO updateFacility(@Parameter(description = "ID of a facility") @PathVariable("facilityId") Integer facilityId,
                                      @RequestBody FacilityDTO facilityDTO) {
        Facility facility = convertToEntity(facilityDTO);
        return convertToDTO(facilityService.updateFacility(facility));
    }

    @Operation(summary = "Delete existing facility",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Facility not found",
                            content = @Content)})
    @DeleteMapping("/facilities/{facilityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFacility(@Parameter(description = "ID of a facility") @PathVariable("facilityId") Integer facilityId) {
        facilityService.deleteFacility(facilityId);
    }

    @Operation(summary = "Get facility list of a specific building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedRes.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Building not found",
                    content = @Content)
    })
    @GetMapping("/buildings/{buildingId}/facilities")
    public Page<Facility> findFacilitiesOfBuilding(@Parameter(description = "ID of a building") @PathVariable("buildingId") Integer buildingId,
                                                       @Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                                       @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return facilityService.findFacilitiesOfBuilding(offset, limit, buildingId);
    }

    private Facility convertToEntity(FacilityDTO facilityDTO) {
        return modelMapper.map(facilityDTO, Facility.class);
    }

    private FacilityDTO convertToDTO(Facility facility) {
        return modelMapper.map(facility, FacilityDTO.class);
    }
}

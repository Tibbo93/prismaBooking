package it.prisma.prismabooking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.building.BuildingDTO;
import it.prisma.prismabooking.service.BuildingService;
import it.prisma.prismabooking.utils.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@Tag(name = "buildings", description = "The Building API")
public class BuildingController {

    final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @Operation(summary = "Get building list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content)})
    @GetMapping("/buildings")
    @ResponseStatus(HttpStatus.OK)
    public Page<Building> findPage(@Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                   @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return buildingService.findPage2(offset, limit);
    }

    @Operation(summary = "Add new building",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Building object that needs to be added", required = true),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "405", description = "Invalid input",
                            content = @Content)})
    @PostMapping("/buildings")
    @ResponseStatus(HttpStatus.CREATED)
    public Building createBuilding(@RequestBody Building building) {
        if (building.getId() != null)
            throw new BadRequestException("Cannot POST resource that already have an ID");
        return buildingService.createResource2(building);
    }

    @Operation(summary = "Find building by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Building.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Building not found",
                            content = @Content)})
    @GetMapping("/buildings/{buildingId}")
    @ResponseStatus(HttpStatus.OK)
    public Building findBuilding(@Parameter(description = "ID of a building") @PathVariable("buildingId") Integer buildingId) {
        return buildingService.findResource2(buildingId);
    }

    @Operation(summary = "Update existing building",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Building object that needs to be updated", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Building not found",
                            content = @Content)})
    @PutMapping("/buildings/{buildingId}")
    @ResponseStatus(HttpStatus.OK)
    public Building updateBuilding(@Parameter(description = "ID of a building") @PathVariable("buildingId") Integer buildingId,
                                   @RequestBody BuildingDTO buildingDTO) {
        return buildingService.updateBuilding(buildingDTO, buildingId);
    }

    @Operation(summary = "Delete existing building",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Building not found",
                            content = @Content)})
    @DeleteMapping("/buildings/{buildingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBuilding(@Parameter(description = "ID of a building") @PathVariable("buildingId") Integer buildingId) {
        buildingService.deleteBuilding(buildingId);
    }

    @Operation(summary = "Get building list of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedRes.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping("/users/{userId}/buildings")
    public PagedRes<Building> findBuildingsOfUser(@Parameter(description = "ID of a user") @PathVariable("userId") String userId,
                                              @Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                              @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return buildingService.findBuildingsOfUser(offset, limit, userId);
    }

    @Operation(summary = "Get building list of a specific facility")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedRes.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Facility not found",
                    content = @Content)
    })
    @GetMapping("/facilities/{facilityId}/buildings")
    public PagedRes<Building> findBuildingsOfFacility(@Parameter(description = "ID of a facility") @PathVariable("facilityId") String facilityId,
                                                  @Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                                  @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return buildingService.findBuildingsOfFacility(offset, limit, facilityId);
    }
}

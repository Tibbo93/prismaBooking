package it.prisma.prismabooking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.room.Room;
import it.prisma.prismabooking.service.RoomService;
import it.prisma.prismabooking.utils.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/buildings/{buildingId}/rooms")
@Tag(name = "rooms", description = "The Room API")
public class RoomController {

    final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get room list")
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedRes<Room> findPage(@Parameter(description = "ID of a building") @PathVariable("buildingId") String buildingId,
                                   @Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                   @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return roomService.findPage(offset, limit, buildingId);
    }

    @Operation(summary = "Add new room",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Room object that needs to be added", required = true),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Building not found",
                            content = @Content),
                    @ApiResponse(responseCode = "405", description = "Invalid input",
                            content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@Parameter(description = "ID of a building") @PathVariable("buildingId") String buildingId,
                           @RequestBody Room room) {
        if (room.getId() != null)
            throw new BadRequestException("Cannot POST resource that already have an ID");
        //if (room.getBuildingId() != null)
          //  throw new BadRequestException("Cannot POST resource that already have a building ID");
        return roomService.createRoom(room, buildingId);
    }

    @Operation(summary = "Find room by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Room.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Room or Building not found",
                            content = @Content)})
    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Room findRoom(@Parameter(description = "ID of a building") @PathVariable("buildingId") String buildingId,
                         @Parameter(description = "ID of a room") @PathVariable("roomId") String roomId) {
        return roomService.findRoom(roomId, buildingId);
    }

    @Operation(summary = "Update existing room",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Room object that needs to be updated", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Room or Building not found",
                            content = @Content)})
    @PutMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Room updateRoom(@Parameter(description = "ID of a building") @PathVariable("buildingId") String buildingId,
                           @Parameter(description = "ID of a room") @PathVariable("roomId") String roomId,
                           @RequestBody Room room) {
        return roomService.createRoom(room, buildingId);
    }

    @Operation(summary = "Delete existing room",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Room or Building not found",
                            content = @Content)})
    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@Parameter(description = "ID of a building") @PathVariable("buildingId") String buildingId,
                           @Parameter(description = "ID of a room") @PathVariable("roomId") String roomId) {
        roomService.deleteRoom(roomId, buildingId);
    }
}

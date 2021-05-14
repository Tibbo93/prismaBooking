package it.prisma.prismabooking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.user.User;
import it.prisma.prismabooking.service.UserService;
import it.prisma.prismabooking.utils.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@Tag(name = "users", description = "The User API")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedRes.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content)})
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public PagedRes<User> findUsers(@Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                    @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return userService.findPage(offset, limit);
    }

    @Operation(summary = "Add new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object that needs to be added", required = true),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "405", description = "Invalid input",
                            content = @Content)})
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        if (user.getId() != null)
            throw new BadRequestException("Cannot POST resource that already have an ID");
        return userService.createUser(user);
    }

    @Operation(summary = "Find user by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content)})
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User findUser(@Parameter(description = "ID of a user") @PathVariable("userId") String userId) {
        return userService.findResource(userId);
    }

    @Operation(summary = "Update existing user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object that needs to be updated", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content)})
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Parameter(description = "ID of a user") @PathVariable("userId") String userId,
                           @RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Delete existing user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content),
                    @ApiResponse(responseCode = "403", description = "Access is forbidden to the resource",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content)})
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Parameter(description = "ID of a user") @PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }

    @Operation(summary = "Get user list of a specific building")
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
    @GetMapping("/buildings/{buildingId}/users")
    public PagedRes<User> findUsersByBuilding(@Parameter(description = "ID of a building") @PathVariable("buildingId") String buildingId,
                                                  @Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                                  @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return userService.findUsersByBuilding(offset, limit, buildingId);
    }
}

package it.prisma.prismabooking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.prisma.prismabooking.model.user.User;
import it.prisma.prismabooking.service.UserService;
import it.prisma.prismabooking.utils.exceptions.BadRequestException;
import org.springframework.data.domain.Page;
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
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content)})
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> findUsers(@Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
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
    public User findUser(@Parameter(description = "ID of a user") @PathVariable("userId") Integer userId) {
        return userService.findUser(userId);
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
    public User updateUser(@Parameter(description = "ID of a user") @PathVariable("userId") Integer userId,
                           @RequestBody User user) {
        return userService.updateUser(user, userId);
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
    public void deleteUser(@Parameter(description = "ID of a user") @PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
    }

    @Operation(summary = "Get user list of a specific building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden to the resources",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Building not found",
                    content = @Content)
    })
    @GetMapping("/buildings/{buildingId}/users")
    public Page<User> findUsersByBuilding(@Parameter(description = "ID of a building") @PathVariable("buildingId") Integer buildingId,
                                          @Parameter(description = "The offset of the first item in the collection to return") @RequestParam Integer offset,
                                          @Parameter(description = "The maximum number of entries to return") @RequestParam Integer limit) {
        return userService.findUsersByBuilding(offset, limit, buildingId);
    }
}

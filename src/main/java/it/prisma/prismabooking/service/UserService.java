package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.building.Building;
import it.prisma.prismabooking.model.user.User;
import it.prisma.prismabooking.repository.UserRepository;
import it.prisma.prismabooking.utils.exceptions.InternalServerErrorException;
import it.prisma.prismabooking.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    protected final ConfigurationComponent config;
    private final UserRepository userRepository;
    private BuildingService buildingService;

    public UserService(ConfigurationComponent configurationComponent, UserRepository userRepository) {
        this.config = configurationComponent;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public Page<User> findPage(Integer offset, Integer limit) {
        return userRepository.findAll(PageRequest.of(offset, limit));
    }

    public User createUser(User user) {
        Set<Building> buildings = new HashSet<>();
        user.getBuildings().stream()
                .map(Building::getId)
                .forEach(id -> buildings.add(buildingService.findBuilding(id)));
        user.setBuildings(buildings);
        return userRepository.save(user);
    }

    public User findUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public User updateUser(User user, Integer userId) {
        User userInDB = findUser(userId);

        Arrays.stream(user.getClass().getDeclaredFields())
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        if (f.get(user) != null)
                            f.set(userInDB, f.get(user));
                    } catch (IllegalAccessException e) {
                        throw new InternalServerErrorException(e.getMessage());
                    }
                });
        return userRepository.save(userInDB);
    }

    public void deleteUser(Integer userId) {
        findUser(userId);
        userRepository.deleteById(userId);
    }

    public Page<User> findUsersByBuilding(Integer offset, Integer limit, Integer buildingId) {
        Building building = buildingService.findBuilding(buildingId);
        Pageable page = PageRequest.of(offset, limit);
        return userRepository.findAllByBuildings(page, building);
    }
}

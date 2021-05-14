package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User> {

    @Value("file:src/main/resources/data/users.json")
    Resource usersFile;
    BuildingService buildingService;

    public UserService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public User createUser(User user) {
        if (!Optional.ofNullable(user.getId()).isPresent())
            user.setId(UUID.randomUUID().toString());
        else {
            findResource(user.getId());
            deleteUser(user.getId());
        }

        list.add(user);
        writeOnJSON(usersFile, user);
        return user;
    }

    public void deleteUser(String userId) {
        list.remove(findResource(userId));
        deleteFromJSON(usersFile);
    }

    public PagedRes<User> findUsersByBuilding(Integer offset, Integer limit, String buildingId) {
        return findPage(offset, limit, list.stream()
                .filter(user -> user.getBuildingsId().contains(buildingId))
                .collect(Collectors.toList()));
    }

    @PostConstruct
    public void init() {
        loadJSON(usersFile, User.class);
    }
}

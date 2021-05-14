package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.PagedRes;
import it.prisma.prismabooking.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User> {

    BuildingService buildingService;

    public UserService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
        super.resourceFile = new DefaultResourceLoader().getResource("file:src/main/resources/data/users.json");
    }

    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    public PagedRes<User> findUsersByBuilding(Integer offset, Integer limit, String buildingId) {
        return findPage(offset, limit, list.stream()
                .filter(user -> user.getBuildingsId().contains(buildingId))
                .collect(Collectors.toList()));
    }

    @PostConstruct
    public void init() {
        loadJSON(User.class);
    }
}

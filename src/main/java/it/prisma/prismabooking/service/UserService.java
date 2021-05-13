package it.prisma.prismabooking.service;

import it.prisma.prismabooking.component.ConfigurationComponent;
import it.prisma.prismabooking.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseService<User> {

    @Value("file:src/main/resources/data/users.json")
    Resource usersFile;

    public UserService(ConfigurationComponent configurationComponent) {
        super.config = configurationComponent;
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

    @PostConstruct
    public void init() {
        loadJSON(usersFile, User.class);
    }
}

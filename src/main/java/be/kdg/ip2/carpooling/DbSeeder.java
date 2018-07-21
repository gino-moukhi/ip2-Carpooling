package be.kdg.ip2.carpooling;

import be.kdg.ip2.carpooling.domain.user.*;
import be.kdg.ip2.carpooling.service.UserService;
import be.kdg.ip2.carpooling.service.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DbSeeder implements CommandLineRunner {
    private UserService userService;

    @Autowired
    public DbSeeder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User gino = new User("gino.moukhi@student.kdg.be", "Snor123",
                new Name("Gino", "Moukhi"),
                new Address("Brugstraat", 103, 2960, "Brecht"),
                21, Gender.MALE, false,
                new Vehicle("Mercedes", VehicleType.SEDAN, 4.2, 3));

        User jimmy = new User("jimmy.kotton@gmail.com", "Gigedy",
                new Name("Jimmy", "Kotton"),
                new Address("Eikenlei", 8, 2960, "Brecht"),
                25, Gender.MALE, true,
                new Vehicle("Dodge", VehicleType.SUV, 8.0, 4));

        User sophie = new User("sophie.kotton@gmail.com", "Barbie",
                new Name("Sophie", "Kotton"),
                new Address("Wilgendaalstraat", 15, 2900, "Schoten"),
                27, Gender.FEMALE, true,
                new Vehicle("Toyota", VehicleType.HATCHBAG, 5.1, 3));

        // DELETE
        //userService.deleteAll();
        // INSERT OR UPDATE (depends on the fact that the users exists in the collection or not)
        List<User> users = Arrays.asList(gino, jimmy, sophie);
        users.forEach(user -> {
            try {
                userService.saveWithCheck(user, false);
            } catch (UserServiceException e) {
                log.error("Something went wrong when calling the SaveWithCheck function in the userService " + e);
            }
        });
    }
}

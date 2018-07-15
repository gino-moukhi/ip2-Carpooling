package be.kdg.ip2.carpooling;

import be.kdg.ip2.carpooling.domain.*;
import be.kdg.ip2.carpooling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {
    private UserRepository userRepo;

    @Autowired
    public DbSeeder(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        User gino = new User("gino.moukhi@student.kdg.be", "Snor123",
                new Name("Gino", "Moukhi"),
                new Address("Brugstraat", 103, 2960, "Brecht"),
                21, Gender.MALE, false,
                new Vehicle("Mercedes", "CLA180", 4.2, 3));

        User jimmy = new User("jimmy.kotton@gmail.com", "Gigedy",
                new Name("Jimmy", "Kotton"),
                new Address("Eikenlei", 8, 2960, "Brecht"),
                25, Gender.MALE, true,
                new Vehicle("Dodge", "Ram", 8.0, 4));

        User sophie = new User("sophie.kotton@gmail.com", "Barbie",
                new Name("Sophie", "Kotton"),
                new Address("Wilgendaalstraat", 15, 2900, "Schoten"),
                27, Gender.FEMALE, true,
                new Vehicle("Toyota", "Yaris", 5.1, 3));

        // DROP
        userRepo.deleteAll();
        // ADD
        List<User> users = Arrays.asList(gino, jimmy, sophie);
        userRepo.saveAll(users);
    }
}

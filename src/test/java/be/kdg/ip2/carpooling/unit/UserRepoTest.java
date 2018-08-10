package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.domain.user.Address;
import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.domain.user.Vehicle;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.repository.user.UserRepository;
//import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepoTest {
    @Autowired
    private UserRepository repo;

    //IMPORTANT NOTICE: Save is an UPSERT!
    //IMPORTANT NOTICE: If user does not have an id it will be saved as a new user if it does have an id it wil just update

    @Before
    public void setUp() {

        //Uses the DbSeeder to create objects
    }

    @Test
    public void testDbSeeder() {
        /*assertEquals(3,repo.findAll().size());
        User user1 = repo.findUserByEmail("gino.moukhi@student.kdg.be");
        User user2 = repo.findUserByEmail("jimmy.kotton@gmail.com");
        User user3 = repo.findUserByEmail("sophie.kotton@gmail.com");
        Address address = new Address("Eikenlei", 8, 2960, "Brecht");
        Vehicle vehicle = new Vehicle("Toyota", VehicleType.HATCHBACK, 5.1, 3);
        assertEquals("Gino Moukhi", user1.getName().toString());
        assertEquals(address.getStreet(), user2.getAddress().getStreet());
        assertEquals(address.getCity(), user2.getAddress().getCity());
        assertEquals(address.getStreetNumber(), user2.getAddress().getStreetNumber());
        assertEquals(address.getZipCode(), user2.getAddress().getZipCode());
        assertEquals(vehicle.getBrand(), user3.getVehicle().getBrand());
        assertEquals(vehicle.getType(), user3.getVehicle().getType());
        assertThat(vehicle.getFuelConsumption(), equalTo(user3.getVehicle().getFuelConsumption()));
        assertEquals(vehicle.getNumberOfPassengers(), user3.getVehicle().getNumberOfPassengers());*/
    }

    @Test
    public void testFetchData() {
        /*User user1 = repo.findUserByEmail("gino.moukhi@student.kdg.be");
        assertNotNull(user1);
        assertEquals("Snor123", user1.getPassword());
        assertEquals(2, repo.findAll().size());*/
    }

    @Test
    public void testDataUpdate() {
        /*User user2 = repo.findUserByEmail("kris.demuynck@kdg.be");
        user2.setPassword("Snor789");
        //repo.save(user2);
        repo.save(user2);
        User user3 = repo.findUserByEmail("kris.demuynck@kdg.be");
        assertEquals("Snor789", user3.getPassword());
        //log.warn(user3.getPassword());
        assertEquals(2, repo.findAll().size());*/
    }

    @After
    public void tearDown() {
        //repo.deleteAll();
    }

}

package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.QUser;
import be.kdg.ip2.carpooling.domain.User;
import be.kdg.ip2.carpooling.repository.UserRepository;
import be.kdg.ip2.carpooling.service.UserService;
import be.kdg.ip2.carpooling.service.UserServiceException;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.findAllUsers();
    }

    @PutMapping
    public void insert(@RequestBody User user) {
        try {
            userService.addUser(user);
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }

    @PostMapping
    public void update(@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        try {
            userService.deleteUser(id);
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/age/{age}")
    public List<User> getByMaxAge(@PathVariable("age")int max) {
        List<User> foundUsers = new ArrayList<>();
        try {
            foundUsers = userService.findUserByAgelessThan(max);
        } catch (UserServiceException e) {
            //ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            e.printStackTrace();
        }
        return foundUsers;
    }

    @GetMapping("/address/city/{city}")
    public List<User> getByCity(@PathVariable("city")String city) {
        List<User> foundUsers = new ArrayList<>();
        try {
            foundUsers = userService.findByCity(city);
        } catch (UserServiceException e) {
            //ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            e.printStackTrace();
        }
        return foundUsers;
    }

    @GetMapping("/address/street/{street}")
    public List<User> getByStreetname(@PathVariable("street")String street) {
        List<User> foundUsers = new ArrayList<>();
        try {
            foundUsers = userService.findByStreet(street);
        } catch (UserServiceException e) {
            //ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            e.printStackTrace();
        }
        return foundUsers;
    }

    @GetMapping("/recommended/{city}/{passengers}")
    public List<User> getRecommended(@PathVariable("city")String city,@PathVariable("passengers")int passengers) {
        List<User> foundUsers = new ArrayList<>();
        try {
            foundUsers = userService.findClosestWithEnoughSpace(city,passengers);
        } catch (UserServiceException e) {
            //ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            e.printStackTrace();
        }
        return foundUsers;
    }
}

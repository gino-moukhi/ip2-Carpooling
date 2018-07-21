package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.UserDto;
import be.kdg.ip2.carpooling.service.UserService;
import be.kdg.ip2.carpooling.service.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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

    @PostMapping
    public User insert(@RequestBody UserDto userDto) throws UserServiceException {
        User user = new User(userDto);
        return userService.addUser(user);
    }

    @PutMapping
    public User update(@RequestBody User user) throws UserServiceException {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        try {
            userService.deleteUser(id);
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id")String id) throws UserServiceException {
        return userService.findUserById(id);
    }

    @GetMapping("/age/{age}")
    public List<User> getByMaxAge(@PathVariable("age")int max) {
        List<User> foundUsers = new ArrayList<>();
        try {
            foundUsers = userService.findUserByAgelessThan(max);
        } catch (UserServiceException e) {
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
            e.printStackTrace();
        }
        return foundUsers;
    }
}

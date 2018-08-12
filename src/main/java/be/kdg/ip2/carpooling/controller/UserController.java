package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.user.RouteUser;
import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.UserDto;
import be.kdg.ip2.carpooling.service.route.RouteService;
import be.kdg.ip2.carpooling.service.route.RouteServiceException;
import be.kdg.ip2.carpooling.service.user.UserService;
import be.kdg.ip2.carpooling.service.user.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private RouteService routeService;

    @Autowired
    public UserController(UserService userService, RouteService routeService) {
        this.userService = userService;
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public List<UserDto> getAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id")String id) throws UserServiceException {
        return userService.findUserDtoById(id);
    }

    @PostMapping
    public UserDto insert(@RequestBody UserDto user) throws UserServiceException {
        return userService.addUserAsDto(user);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto user) throws UserServiceException, RouteServiceException {
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            routeService.updateUsersOfRoute(new RouteUser(updatedUser));
            return new UserDto(updatedUser);
        } else {
            return new UserDto(userService.findUserById(user.getId()));
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
}
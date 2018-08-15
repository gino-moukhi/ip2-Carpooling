package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.LoginUserDto;
import be.kdg.ip2.carpooling.dto.UserDto;
import be.kdg.ip2.carpooling.service.user.UserService;
import be.kdg.ip2.carpooling.service.user.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public LoginUserDto login(@RequestBody LoginUserDto loginUser) {
        return userService.signIn(loginUser);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto userDto) throws UserServiceException {
        return userService.signUp(new User(userDto));
    }
}

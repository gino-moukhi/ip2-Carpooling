package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.user.LoginUser;
import be.kdg.ip2.carpooling.dto.UserDto;
import be.kdg.ip2.carpooling.service.user.UserService;
import be.kdg.ip2.carpooling.service.user.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody UserDto userDto) throws UserServiceException {
        return userService.addUserAsDto(userDto);
    }
}

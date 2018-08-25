package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.LoginUserDto;
import be.kdg.ip2.carpooling.dto.RegisterUserDto;
import be.kdg.ip2.carpooling.dto.UserDto;
import be.kdg.ip2.carpooling.service.authentication.AuthenticationService;
import be.kdg.ip2.carpooling.service.user.UserService;
import be.kdg.ip2.carpooling.service.user.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private AuthenticationService authService;

    @Autowired
    public AuthenticationController(@Qualifier("authenticationServiceImpl") AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public LoginUserDto login(@RequestBody LoginUserDto loginUser) {
        return authService.signIn(loginUser);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto userDto) throws UserServiceException {
        return authService.signUp(new User(userDto));
    }

    @PostMapping("/signup/simple")
    public RegisterUserDto signUp(@RequestBody RegisterUserDto userDto) throws UserServiceException {
        return authService.signUp(userDto);
    }
}

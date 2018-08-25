package be.kdg.ip2.carpooling.service.authentication;

import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.LoginUserDto;
import be.kdg.ip2.carpooling.dto.RegisterUserDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AuthenticationService {
    LoginUserDto signIn(LoginUserDto loginUser);

    String signUp(User user);

    RegisterUserDto signUp(RegisterUserDto registerUserDto);

    User whoami(HttpServletRequest req);
}

package be.kdg.ip2.carpooling.service.authentication;

import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.LoginUserDto;
import be.kdg.ip2.carpooling.dto.RegisterUserDto;
import be.kdg.ip2.carpooling.repository.user.UserRepository;
import be.kdg.ip2.carpooling.security.CustomException;
import be.kdg.ip2.carpooling.security.JwtTokenProvider;
import be.kdg.ip2.carpooling.service.user.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder encoder, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public LoginUserDto signIn(LoginUserDto loginUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            User foundUser = userRepository.findUserByEmail(loginUser.getUsername());
            String token = tokenProvider.createToken(loginUser.getUsername(), foundUser.getRoles());
            loginUser.setId(foundUser.getId());
            loginUser.setToken(token);
            return loginUser;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        //return null;
    }

    public String signUp(User user) {
        if (userRepository.findUserByEmail(user.getEmail()) == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return tokenProvider.createToken(user.getEmail(), user.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        //return null;
    }

    @Override
    public RegisterUserDto signUp(RegisterUserDto registerUserDto) {
        if (userRepository.findUserByEmail(registerUserDto.getUsername()) == null) {
            registerUserDto.setPassword(encoder.encode(registerUserDto.getPassword()));
            User savedUser = userRepository.save(new User(registerUserDto));
            String token = tokenProvider.createToken(registerUserDto.getUsername(), new ArrayList<>());
            registerUserDto.setId(savedUser.getId());
            registerUserDto.setToken(token);
            return registerUserDto;
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        //return null;
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findUserByEmail(tokenProvider.getUsername(tokenProvider.resolveToken(req)));
    }

    private User saveWithCheck(User user) throws UserServiceException {
        User foundUser = userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (foundUser != null) {
            user.setId(foundUser.getId());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}

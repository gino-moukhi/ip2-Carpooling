package be.kdg.ip2.carpooling.service.user;

import be.kdg.ip2.carpooling.domain.user.QUser;
import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.LoginUserDto;
import be.kdg.ip2.carpooling.dto.UserDto;
import be.kdg.ip2.carpooling.repository.user.UserRepository;
import be.kdg.ip2.carpooling.security.CustomException;
import be.kdg.ip2.carpooling.security.JwtTokenProvider;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User findUserById(String id) throws UserServiceException {
        return userRepository.findUserById(id);
    }

    @Override
    public UserDto findUserDtoById(String id) throws UserServiceException {
        return new UserDto(userRepository.findUserById(id));
    }

    @Override
    public User findUserByEmail(String email) throws UserServiceException {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> all = userRepository.findAll();
        List<UserDto> allDto = new ArrayList<>();
        all.forEach(user -> allDto.add(new UserDto(user)));
        return allDto;
    }

    @Override
    public User addUser(UserDto user) throws UserServiceException {
        return saveWithCheck(new User(user));
    }

    @Override
    public UserDto addUserAsDto(UserDto user) throws UserServiceException {
        return new UserDto(saveWithCheck(new User(user)));
    }

    @Override
    public User addUser(User user) throws UserServiceException {
        return saveWithCheck(user);
    }

    @Override
    public User updateUser(UserDto user) throws UserServiceException {
        User userToUpdate = new User(user);
        if (userRepository.findUserById(userToUpdate.getId()).equals(userToUpdate)) {
            return null;
        } else {
            return userRepository.save(userToUpdate);
        }
    }

    @Override
    public UserDto updateUserAsDto(UserDto user) throws UserServiceException {
        User userToUpdate = new User(user);
        if (userRepository.findUserById(userToUpdate.getId()).equals(userToUpdate)) {
            return null;
        } else {
            return new UserDto(userRepository.save(userToUpdate));
        }
    }

    @Override
    public User updateUser(User user) throws UserServiceException {
        if (userRepository.findUserById(user.getId()).equals(user)) {
            return null;
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(String id) throws UserServiceException {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<User> findUserByAgelessThan(int max) throws UserServiceException {
        return userRepository.findUserByAgeLessThan(max);
    }

    //This method uses the query created with the @Query annotation in UserRepository
    @Override
    public List<User> findByCity(String city) throws UserServiceException {
        return userRepository.findByCity(city);
    }

    @Override
    public List<User> findByStreet(String street) throws UserServiceException {
        List<User> all2 = userRepository.findUsersByAddress_Street(street);
        log.info(all2.toString());
        return all2;
    }

    @Override
    public List<User> findClosestWithEnoughSpace(String city, int passengers) throws UserServiceException {
        QUser qUser = new QUser("user");
        BooleanExpression filterByCity = qUser.address.city.eq(city);
        BooleanExpression filterByPassengerSpace = qUser.vehicle.numberOfPassengers.goe(passengers);
        return (List<User>) userRepository.findAll(filterByCity.and(filterByPassengerSpace));
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

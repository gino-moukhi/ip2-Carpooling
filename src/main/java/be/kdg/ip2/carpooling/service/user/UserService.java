package be.kdg.ip2.carpooling.service.user;

import be.kdg.ip2.carpooling.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findUserById(String id) throws UserServiceException;

    User findUserByEmail(String email) throws UserServiceException;

    List<User> findAllUsers();

    User addUser(User user) throws UserServiceException;

    User updateUser(User user) throws UserServiceException;

    void deleteUser(String id) throws UserServiceException;

    void deleteAll();

    User saveWithCheck(User user, boolean useIdOrEmail) throws UserServiceException;

    List<User> findUserByAgelessThan(int max) throws UserServiceException;

    List<User> findByCity(String city) throws UserServiceException;

    List<User> findByStreet(String street) throws UserServiceException;

    List<User> findClosestWithEnoughSpace(String city, int passengers) throws UserServiceException;
}

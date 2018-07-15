package be.kdg.ip2.carpooling.service;

import be.kdg.ip2.carpooling.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findUserById(String id) throws UserServiceException;

    User findUserByEmail(String email) throws UserServiceException;

    List<User> findAllUsers();

    void addUser(User user) throws UserServiceException;

    void updateUser(User user) throws UserServiceException;

    void deleteUser(String id) throws UserServiceException;

    void deleteAll();

    void saveWithCheck(User user) throws UserServiceException;

    List<User> findUserByAgelessThan(int max) throws UserServiceException;

    List<User> findByCity(String city) throws UserServiceException;

    List<User> findByStreet(String street) throws UserServiceException;

    List<User> findClosestWithEnoughSpace(String city, int passengers) throws UserServiceException;
}

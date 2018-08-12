package be.kdg.ip2.carpooling.service.user;

import be.kdg.ip2.carpooling.domain.user.User;
import be.kdg.ip2.carpooling.dto.UserDto;

import java.util.List;

public interface UserService {
    User findUserById(String id) throws UserServiceException;

    UserDto findUserDtoById(String id) throws UserServiceException;

    User findUserByEmail(String email) throws UserServiceException;

    List<UserDto> findAllUsers();

    User addUser(UserDto user) throws UserServiceException;

    UserDto addUserAsDto(UserDto user) throws UserServiceException;

    User addUser(User user) throws UserServiceException;

    User updateUser(UserDto user) throws UserServiceException;

    UserDto updateUserAsDto(UserDto user) throws UserServiceException;

    User updateUser(User user) throws UserServiceException;

    void deleteUser(String id) throws UserServiceException;

    void deleteAll();

    List<User> findUserByAgelessThan(int max) throws UserServiceException;

    List<User> findByCity(String city) throws UserServiceException;

    List<User> findByStreet(String street) throws UserServiceException;

    List<User> findClosestWithEnoughSpace(String city, int passengers) throws UserServiceException;
}

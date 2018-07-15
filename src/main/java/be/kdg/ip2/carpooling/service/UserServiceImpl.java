package be.kdg.ip2.carpooling.service;

import be.kdg.ip2.carpooling.configuration.SpringMongoConfig;
import be.kdg.ip2.carpooling.domain.QUser;
import be.kdg.ip2.carpooling.domain.User;
import be.kdg.ip2.carpooling.repository.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    private MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(String id) throws UserServiceException {
        //mongoOperations.findById(id,User.class);
        return null;
    }

    @Override
    public User findUserByEmail(String email) throws UserServiceException {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        if (users == null) {
            return new ArrayList<>();
        } else {
            return users;
        }
    }

    @Override
    public void addUser(User user) throws UserServiceException {
        userRepository.insert(user);
    }

    @Override
    public void updateUser(User user) throws UserServiceException {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) throws UserServiceException {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findUserByAgelessThan(int max) throws UserServiceException {
        return userRepository.findUserByAgeLessThan(max);
    }

    @Override
    public List<User> findByCity(String city) throws UserServiceException {
        return userRepository.findByCity(city);
    }

    @Override
    public List<User> findByStreet(String street) throws UserServiceException {
        QUser qUser = new QUser("user");
        BooleanExpression filterByStreet = qUser.address.street.eq(street);

        return (List<User>) userRepository.findAll(filterByStreet);
    }

    @Override
    public List<User> findClosestWithEnoughSpace(String city, int passengers) throws UserServiceException {
        QUser qUser = new QUser("user");
        BooleanExpression filterByCity = qUser.address.city.eq(city);
        BooleanExpression filterByPassengerSpace = qUser.vehicle.numberOfPassengers.goe(passengers);
        return (List<User>) userRepository.findAll(filterByCity.and(filterByPassengerSpace));
    }
}

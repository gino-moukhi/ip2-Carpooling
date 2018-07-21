package be.kdg.ip2.carpooling.repository;

import be.kdg.ip2.carpooling.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User> {
    //IMPORTANT: the MongoRepository can find items form a domain model if they are a basic type
    //the moment you introduce a custom object (like address) you will need to use a query to get the data

    User findUserById(String id);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    List<User> findUserByAgeLessThan(int maxAge);

    @Query(value = "{'address.city':?0}")
    List<User> findByCity(String city);
}

package be.kdg.ip2.carpooling.repository.user;

import be.kdg.ip2.carpooling.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User> {
    User findUserById(String id);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    List<User> findUserByAgeLessThan(int maxAge);

    @Query(value = "{'address.city':?0}")
    List<User> findByCity(String city);

    List<User> findUsersByAddress_Street(String streetName);
}

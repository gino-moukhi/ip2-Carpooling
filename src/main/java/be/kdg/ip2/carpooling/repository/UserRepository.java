package be.kdg.ip2.carpooling.repository;

import be.kdg.ip2.carpooling.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

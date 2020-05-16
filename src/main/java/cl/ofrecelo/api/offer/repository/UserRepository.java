package cl.ofrecelo.api.offer.repository;

import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
}

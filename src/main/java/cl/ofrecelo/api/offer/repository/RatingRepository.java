package cl.ofrecelo.api.offer.repository;

import cl.ofrecelo.api.offer.model.Rating;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
    public List<Rating> findAllByOffer_Id(ObjectId offerId);
}

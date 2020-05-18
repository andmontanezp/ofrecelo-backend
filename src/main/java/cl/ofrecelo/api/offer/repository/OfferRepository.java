package cl.ofrecelo.api.offer.repository;

import cl.ofrecelo.api.offer.model.Offer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OfferRepository extends MongoRepository<Offer, ObjectId> {

    List<Offer> findAllByAddress_District(String district);
    List<Offer> findAllByUserId(String userId);
}

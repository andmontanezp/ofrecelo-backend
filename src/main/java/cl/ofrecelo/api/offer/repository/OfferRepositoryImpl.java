package cl.ofrecelo.api.offer.repository;

import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class OfferRepositoryImpl implements OfferRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public OfferRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Offer> findByUser(ObjectId user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(user));
        Offer offer = mongoTemplate.findOne(query, Offer.class);
        return null;
    }
}

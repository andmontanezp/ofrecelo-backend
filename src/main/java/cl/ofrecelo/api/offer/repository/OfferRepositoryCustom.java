package cl.ofrecelo.api.offer.repository;

import cl.ofrecelo.api.offer.model.Offer;
import org.bson.types.ObjectId;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.PolishNumberValidator;

import java.util.List;

public interface OfferRepositoryCustom {

    List<Offer> findByUser(ObjectId user);
}

package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import cl.ofrecelo.api.offer.repository.UserRepository;
import org.springframework.stereotype.Service;

public interface OfferService {
     Offer saveOffer(Offer offer);
}

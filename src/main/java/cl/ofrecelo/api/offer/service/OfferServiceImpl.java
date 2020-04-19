package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.model.UserInformation;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private UserInformation userInformation;

    public OfferServiceImpl(OfferRepository offerRepository, UserInformation userInformation) {
        this.offerRepository = offerRepository;
        this.userInformation = userInformation;
    }

    @Override
    public Offer saveOffer(Offer offer){
        offer.setUserId(userInformation.getUserId());

        return offerRepository.save(offer);
    }
}

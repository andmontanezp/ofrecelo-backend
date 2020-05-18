package cl.ofrecelo.api.offer.transformer;

import cl.ofrecelo.api.offer.dto.OfferDTO;
import cl.ofrecelo.api.offer.dto.RatingDto;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.Rating;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferTransformer implements Transformable<OfferDTO, Offer> {

    private RatingTransformer ratingTransformer;

    public OfferTransformer(RatingTransformer ratingTransformer) {
        this.ratingTransformer = ratingTransformer;
    }

    @Override
    public Offer fromRequestToDomainObject(OfferDTO offerDto) throws Exception {
        return new Offer();
    }

    @Override
    public OfferDTO fromDomainObjectToResponse(Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offer.getId().toString());
        offerDTO.setTitle(offer.getTitle());
        offerDTO.setCoordinates(offer.getCoordinates());
        offerDTO.setRating(ratingTransformer.fromDomainListToResponseList(offer.getRatings()));

        return offerDTO;
    }

    @Override
    public List<OfferDTO> fromDomainListToResponseList(List<Offer> r) throws Exception {
        return null;
    }
}

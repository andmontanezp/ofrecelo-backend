package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.dto.RatingDto;
import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.exception.UserNotFoundException;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.Rating;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import cl.ofrecelo.api.offer.repository.RatingRepository;
import cl.ofrecelo.api.offer.transformer.RatingTransformer;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomRatingService {

    private RatingRepository ratingRepository;

    private OfferRepository offerRepository;

    private RatingTransformer ratingTransformer;

    public CustomRatingService(RatingRepository ratingRepository, OfferRepository offerRepository, RatingTransformer ratingTransformer) {
        this.ratingRepository = ratingRepository;
        this.offerRepository = offerRepository;
        this.ratingTransformer = ratingTransformer;
    }

    public RatingDto saveRating(RatingDto ratingDto) throws OfferNotFoundException, UserNotFoundException {
        Offer offer = this.offerRepository.findById(new ObjectId(ratingDto.getOfferId())).orElseThrow(OfferNotFoundException::new);
        Rating rating = this.ratingRepository.save(ratingTransformer.fromRequestToDomainObject(ratingDto));
        List<Rating> ratings = Optional.ofNullable(offer.getRatings()).orElseGet(ArrayList::new);
        ratings.add(rating);
        offer.setRatings(ratings);
        this.offerRepository.save(offer);
        return ratingTransformer.fromDomainObjectToResponse(rating);
    }

    public List<RatingDto> findAllByOffer_Id(String offerId) {
        List<Rating> allByOffer_id = this.ratingRepository.findAllByOffer_Id(new ObjectId(offerId));
        return this.ratingTransformer.fromDomainListToResponseList(allByOffer_id);
    }

}

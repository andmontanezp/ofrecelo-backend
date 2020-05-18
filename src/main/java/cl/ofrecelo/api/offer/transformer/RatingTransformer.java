package cl.ofrecelo.api.offer.transformer;

import cl.ofrecelo.api.offer.dto.RatingDto;
import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.exception.UserNotFoundException;
import cl.ofrecelo.api.offer.model.Rating;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import cl.ofrecelo.api.offer.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RatingTransformer implements Transformable<RatingDto, Rating> {

    private OfferRepository offerRepository;

    private UserRepository userRepository;

    public RatingTransformer(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Rating fromRequestToDomainObject(RatingDto ratingDto) throws OfferNotFoundException, UserNotFoundException {
        Rating rating = new Rating();
        rating.setOffer(offerRepository.findById(new ObjectId(ratingDto.getOfferId())).orElseThrow(OfferNotFoundException::new));
        rating.setUser(userRepository.findById(new ObjectId(ratingDto.getUserId())).orElseThrow(UserNotFoundException::new));
        rating.setFeedback(ratingDto.getFeedback());
        rating.setScore(ratingDto.getScore());
        return rating;
    }

    @Override
    public RatingDto fromDomainObjectToResponse(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setOfferId(rating.getOffer().getId().toString());
        ratingDto.setUserId(rating.getUser().get_id().toString());
        ratingDto.setScore(rating.getScore());
        ratingDto.setFeedback(rating.getFeedback());
        return ratingDto;
    }

    @Override
    public List<RatingDto> fromDomainListToResponseList(List<Rating> ratings) {
        return Optional.ofNullable(ratings).orElseGet(ArrayList::new).stream()
                .map(this::fromDomainObjectToResponse)
                .collect(Collectors.toList());
    }
}

package cl.ofrecelo.api.offer.controller;

import cl.ofrecelo.api.offer.dto.RatingDto;
import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.exception.UserNotFoundException;
import cl.ofrecelo.api.offer.model.Rating;
import cl.ofrecelo.api.offer.repository.RatingRepository;
import cl.ofrecelo.api.offer.transformer.RatingTransformer;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rate")
public class RatingController {

    private RatingRepository ratingRepository;

    private RatingTransformer ratingTransformer;

    public RatingController(RatingRepository ratingRepository, RatingTransformer ratingTransformer) {
        this.ratingRepository = ratingRepository;
        this.ratingTransformer = ratingTransformer;
    }

    @PostMapping
    public ResponseEntity<RatingDto> rateOrder(@RequestBody RatingDto ratingDto) throws Exception {
        Rating createdRating = ratingRepository.save(ratingTransformer.fromRequestToDomainObject(ratingDto));
        return ResponseEntity.created(URI.create("")).body(ratingTransformer.fromDomainObjectToResponse(createdRating));
    }

    @GetMapping("/order/{offerId}")
    public ResponseEntity<List<RatingDto>> rateOrder(@PathVariable("offerId") String offerId) throws Exception {
        List<Rating> ratings = ratingRepository.findAllByOffer_Id(new ObjectId(offerId));
        return ResponseEntity.ok(ratingTransformer.fromDomainListToResponseList(ratings));
    }
}

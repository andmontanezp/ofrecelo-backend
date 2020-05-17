package cl.ofrecelo.api.offer.controller;

import cl.ofrecelo.api.offer.dto.RatingDto;

import cl.ofrecelo.api.offer.service.CustomRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rate")
public class RatingController {

    private CustomRatingService ratingService;

    public RatingController(CustomRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingDto> rateOrder(@RequestBody RatingDto ratingDto) throws Exception {
        return ResponseEntity.created(URI.create("")).body(ratingService.saveRating(ratingDto));
    }

    @GetMapping("/order/{offerId}")
    public ResponseEntity<List<RatingDto>> rateOrder(@PathVariable("offerId") String offerId) throws Exception {
        return ResponseEntity.ok(ratingService.findAllByOffer_Id(offerId));
    }
}
